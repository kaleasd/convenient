package com.gyh.servicevaluation.task;

import com.gyh.internalcommon.constant.ChargingCategoryEnum;
import com.gyh.internalcommon.dto.valuation.charging.KeyRule;
import com.gyh.internalcommon.dto.valuation.charging.Rule;
import com.gyh.internalcommon.dto.valuation.charging.TagPrice;
import com.gyh.internalcommon.dto.valuation.discount.DiscountCondition;
import com.gyh.internalcommon.dto.valuation.discount.DiscountPrice;
import com.gyh.internalcommon.entity.OrderRulePrice;
import com.gyh.internalcommon.entity.OrderRulePriceDetail;
import com.gyh.internalcommon.entity.OrderRulePriceTag;
import com.gyh.servicevaluation.dao.DynamicDiscountRuleDao;
import com.gyh.servicevaluation.dao.OrderRulePriceDao;
import com.gyh.servicevaluation.dao.OrderRulePriceDetailDao;
import com.gyh.servicevaluation.dao.OrderRulePriceTagDao;
import com.gyh.servicevaluation.dto.DriveMeter;
import com.gyh.servicevaluation.dto.PriceMeter;
import com.gyh.servicevaluation.dto.TimeMeter;
import com.gyh.servicevaluation.util.PriceHelper;
import com.gyh.servicevaluation.util.TimeSlice;
import com.gyh.servicevaluation.util.UnitConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class ValuationTask {

    @Autowired
    private OrderRulePriceDao orderRulePriceDao;

    @Autowired
    private OrderRulePriceDetailDao orderRulePriceDetailDao;

    @Autowired
    private OrderRulePriceTagDao orderRulePriceTagDao;

    @Autowired
    private DynamicDiscountRuleDao dynamicDiscountRuleDao;

    /**
     * ??????????????????
     * @param driveMeter ????????????
     * @return ????????????
     * */
    @Async
    public CompletableFuture<OrderRulePrice> calcMasterPrice(DriveMeter driveMeter) {
        OrderRulePrice rulePrice = new OrderRulePrice();
        Rule rule = driveMeter.getRule();
        // key??????
        rulePrice.setOrderId(driveMeter.getOrder() != null ? driveMeter.getOrder().getId() : driveMeter.getCurrentPriceRequestDto().getOrderId());
        rulePrice.setCategory(driveMeter.getChargingCategoryEnum().getCodeAsString());
        rulePrice.setTotalDistance(UnitConverter.meterToKilo(driveMeter.getTotalDistance()));
        rulePrice.setTotalTime(UnitConverter.secondToMinute(driveMeter.getTotalTime()));
        rulePrice.setCityCode(rule.getKeyRule().getCityCode());
        rulePrice.setCityName(rule.getKeyRule().getCityName());
        rulePrice.setServiceTypeId(rule.getKeyRule().getServiceTypeId());
        rulePrice.setServiceTypeName(rule.getKeyRule().getServiceTypeName());
        rulePrice.setChannelId(rule.getKeyRule().getChannelId());
        rulePrice.setChannelName(rule.getKeyRule().getChannelName());
        rulePrice.setCarLevelId(rule.getKeyRule().getCarLevelId());
        rulePrice.setCarLevelName(rule.getKeyRule().getCarLevelName());
        // ????????????
        rulePrice.setBasePrice(rule.getBasicRule().getBasePrice());
        rulePrice.setBaseKilo(rule.getBasicRule().getKilos());
        rulePrice.setBaseMinute(rule.getBasicRule().getMinutes());
        rulePrice.setLowestPrice(rule.getBasicRule().getLowestPrice());
        rulePrice.setPerKiloPrice(rule.getPriceRule().getPerKiloPrice());
        rulePrice.setPerMinutePrice(rule.getPriceRule().getPerMinutePrice());
        // ????????????
        rulePrice.setNightTime(0D);
        rulePrice.setNightDistance(0D);
        rulePrice.setNightPrice(BigDecimal.ZERO);
        if (rule.getNightRule().getStart() != null && rule.getNightRule().getEnd() != null) {
            rulePrice.setNightStart(rule.getNightRule().getStart());
            rulePrice.setNightEnd(rule.getNightRule().getEnd());
            rulePrice.setNightPerKiloPrice(rule.getNightRule().getPerKiloPrice());
            rulePrice.setNightPerMinutePrice(rule.getNightRule().getPerMinutePrice());

            //??????????????????
            TimeMeter.TimePriceUnit unit = generateTimePriceUnit(driveMeter);
            unit.setStart(UnitConverter.dateToLocalTime(rulePrice.getNightStart()));
            unit.setEnd(UnitConverter.dateToLocalTime(rulePrice.getNightEnd()));
            unit.setPerMeterPrice(UnitConverter.kiloToMeterPrice(rulePrice.getNightPerKiloPrice()));
            unit.setPerSecondPrice(UnitConverter.minuteToSecondPrice(rulePrice.getNightPerMinutePrice()));
            TimeMeter.TimePriceResult result = TimeMeter.measure(generateTimeSlice(driveMeter), unit);
            rulePrice.setNightTime(UnitConverter.secondToMinute(result.getDuration()));
            rulePrice.setNightDistance(UnitConverter.meterToKilo(result.getDistance()));
            rulePrice.setNightPrice(PriceHelper.add(result.getDistancePrice(), result.getTimePrice()));
        }

        //????????????
        rulePrice.setBeyondStartKilo(rule.getBeyondRule().getStartKilo());
        rulePrice.setBeyondPerKiloPrice(rule.getBeyondRule().getPerKiloPrice());
        rulePrice.setBeyondDistance(PriceHelper.subtract(rulePrice.getTotalDistance(), rulePrice.getBeyondStartKilo()).doubleValue());
        rulePrice.setBeyondPrice(PriceHelper.multiply(rulePrice.getBeyondPerKiloPrice(), rulePrice.getBeyondDistance()));

        return new AsyncResult<>(rulePrice).completable();

    }

    /**
     * ????????????????????????
     *
     * @param driveMeter ????????????
     * @return ??????????????????
     */
    @Async
    public CompletableFuture<List<OrderRulePriceDetail>> calcDetailPrice(DriveMeter driveMeter) {
        // ?????????????????????????????????
        TimeSlice totalSlice = generateTimeSlice(driveMeter);
        // ???????????????????????????
        List<OrderRulePriceDetail> details = Optional.ofNullable(driveMeter.getRule().getPriceRule().getTimeRules()).orElse(new ArrayList<>()).stream().map(r -> {
            OrderRulePriceDetail detail = new OrderRulePriceDetail();
            detail.setOrderId(driveMeter.getOrder() != null ? driveMeter.getOrder().getId() : driveMeter.getCurrentPriceRequestDto().getOrderId());
            detail.setCategory(driveMeter.getChargingCategoryEnum().getCodeAsString());
            detail.setStartHour(r.getStart());
            detail.setEndHour(r.getEnd());
            detail.setPerKiloPrice(r.getPerKiloPrice());
            detail.setPerMinutePrice(r.getPerMinutePrice());

            //?????????????????????
            TimeMeter.TimePriceUnit unit = generateTimePriceUnit(driveMeter);
            unit.setStart(LocalTime.of(detail.getStartHour(), 0, 0));
            unit.setEnd(LocalTime.of(detail.getEndHour(), 0, 0).minusSeconds(1));
            unit.setPerMeterPrice(UnitConverter.kiloToMeterPrice(detail.getPerKiloPrice()));
            unit.setPerSecondPrice(UnitConverter.minuteToSecondPrice(detail.getPerMinutePrice()));

            //??????????????????
            TimeMeter.TimePriceResult result = TimeMeter.measure(totalSlice, unit);
            detail.setDuration(UnitConverter.secondToMinute(result.getDuration()));
            detail.setTimePrice(PriceHelper.resetScale(result.getTimePrice()));
            detail.setDistance(UnitConverter.meterToKilo(result.getDistance()));
            detail.setDistancePrice(PriceHelper.resetScale(result.getDistancePrice()));
            return detail;
        }).collect(Collectors.toList());
        return new AsyncResult<>(details).completable();
    }

    /**
     * ??????????????????
     *
     * @param driveMeter ????????????
     * @param master     ????????????????????????
     * @param details    ????????????????????????
     */
    public void calcOtherPrice(DriveMeter driveMeter, OrderRulePrice master, List<OrderRulePriceDetail> details) {
        //???????????????????????????????????????
        if (driveMeter.getRule().getBasicRule().isBasicCharging()) {
            master.setRestDistance(0D);
            master.setRestDistancePrice(BigDecimal.ZERO);
            master.setRestDuration(0D);
            master.setRestDurationPrice(BigDecimal.ZERO);

            master.setPath(PriceHelper.subtract(master.getTotalDistance(), master.getBaseKilo()).doubleValue());
            master.setPathPrice(PriceHelper.multiply(master.getPerKiloPrice(), master.getPath()));
            master.setDuration(PriceHelper.subtract(master.getTotalTime(), master.getBaseMinute()).doubleValue());
            master.setDurationPrice(PriceHelper.multiply(master.getPerMinutePrice(), master.getDuration()));
        } else {
            //???????????????????????????
            master.setRestDistance(PriceHelper.subtract(master.getTotalDistance(), details.stream().mapToDouble(OrderRulePriceDetail::getDistance).sum()).doubleValue());
            master.setRestDistancePrice(PriceHelper.multiply(master.getPerKiloPrice(), master.getRestDistance()));
            master.setRestDuration(PriceHelper.subtract(master.getTotalTime(), details.stream().mapToDouble(OrderRulePriceDetail::getDuration).sum()).doubleValue());
            master.setRestDurationPrice(PriceHelper.multiply(master.getPerMinutePrice(), master.getRestDuration()));

            master.setPath(master.getTotalDistance());
            master.setPathPrice(PriceHelper.add(master.getRestDistancePrice(), details.stream().map(OrderRulePriceDetail::getDistancePrice).reduce(BigDecimal.ZERO, BigDecimal::add)));
            master.setDuration(master.getTotalTime());
            master.setDurationPrice(PriceHelper.add(master.getRestDurationPrice(), details.stream().map(OrderRulePriceDetail::getTimePrice).reduce(BigDecimal.ZERO, BigDecimal::add)));
        }

    }

    /**
     * ??????????????????
     *
     * @param driveMeter ????????????
     * @return ?????????????????????
     */
    public DiscountPrice calcDiscount(DriveMeter driveMeter) {
        KeyRule keyRule = driveMeter.getRule().getKeyRule();
        LocalDateTime start = UnitConverter.dateToLocalDateTime(driveMeter.getOrder() != null ? driveMeter.getOrder().getOrderStartTime() : new Date(driveMeter.getCurrentPriceRequestDto().getStartTime()));

        DiscountCondition condition = new DiscountCondition();
        condition.setCityCode(keyRule.getCityCode());
        condition.setServiceTypeId(keyRule.getServiceTypeId());
        condition.setCarLevelId(keyRule.getCarLevelId());
        condition.setTotalDistance(UnitConverter.meterToKilo(driveMeter.getTotalDistance()));
        condition.setStartHour(start.getHour());
        condition.setStartDate(start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        condition.setStartWeekDay(start.getDayOfWeek().getValue());
        return dynamicDiscountRuleDao.findDiscountByCondition(condition);
    }

    /**
     * ??????????????????
     *
     * @param driveMeter ????????????
     * @return ????????????
     */
    public BigDecimal calcTagPrice(DriveMeter driveMeter) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        //??????????????????
        if (driveMeter.getRule().getTagPrices() != null) {
            totalPrice = driveMeter.getRule().getTagPrices().stream().map(TagPrice::getPrice).reduce(totalPrice, BigDecimal::add);
        }
        return totalPrice;
    }

    /**
     * ?????????????????????????????????
     *
     * @param driveMeter ????????????
     * @return ?????????
     */
    private TimeSlice generateTimeSlice(DriveMeter driveMeter) {
        TimeSlice totalSlice = new TimeSlice();
        totalSlice.setX(driveMeter.getStartDateTime());
        totalSlice.setY(totalSlice.getX().plusSeconds((long) Math.ceil(driveMeter.getTotalTime())));
        return totalSlice;
    }

    /**
     * ???????????????
     *
     * @param chargingCategory ??????????????????
     * @param priceMeter       ???????????????DTO
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateToDb(ChargingCategoryEnum chargingCategory, PriceMeter priceMeter) {
        if (chargingCategory != ChargingCategoryEnum.Forecast && chargingCategory != ChargingCategoryEnum.Settlement) {
            return;
        }

        OrderRulePrice master = priceMeter.getRulePrice();
        List<OrderRulePriceDetail> details = priceMeter.getRulePriceDetails();
        List<TagPrice> tagPrices = priceMeter.getTagPrices();

        int orderId = master.getOrderId();
        orderRulePriceDao.deleteByOrderIdAndCategory(orderId, chargingCategory);
        orderRulePriceDetailDao.deleteByOrderIdAndCategory(orderId, chargingCategory);
        orderRulePriceTagDao.deleteByOrderIdAndCategory(orderId, chargingCategory);

        //??????????????????
        Date now = new Date();
        master.setCreateTime(now);

        //?????????????????????
        orderRulePriceDao.insert(master);

        //?????????????????????????????????
        if (details != null && !details.isEmpty()) {
            details.forEach(detail -> detail.setCreateTime(now));
            orderRulePriceDetailDao.insert(details);
        }

        //???????????????????????????
        if (tagPrices != null && !tagPrices.isEmpty()) {
            List<OrderRulePriceTag> priceTags = tagPrices.stream().map(tagPrice -> {
                OrderRulePriceTag orderRulePriceTag = new OrderRulePriceTag();
                orderRulePriceTag.setOrderId(orderId);
                orderRulePriceTag.setCategory(chargingCategory.getCodeAsString());
                orderRulePriceTag.setTagName(tagPrice.getName());
                orderRulePriceTag.setTagPrice(tagPrice.getPrice());
                orderRulePriceTag.setCreateTime(now);
                return orderRulePriceTag;
            }).collect(Collectors.toList());
            orderRulePriceTagDao.insert(priceTags);
        }
    }

    /**
     * ?????????????????????
     *
     * @param driveMeter ????????????
     * @return ?????????????????????
     */
    private TimeMeter.TimePriceUnit generateTimePriceUnit(DriveMeter driveMeter) {
        TimeMeter.TimePriceUnit unit = null;
        switch (driveMeter.getChargingCategoryEnum()) {
            case Forecast:
                BigDecimal speed = BigDecimal.valueOf(driveMeter.getTotalDistance()).divide(BigDecimal.valueOf(driveMeter.getTotalTime()), 5, RoundingMode.DOWN);
                unit = TimeMeter.TimePriceUnit.instanceByForecast(speed.doubleValue());
                break;
            case Settlement:
            case RealTime:
                int carId = driveMeter.getOrder() != null ? driveMeter.getOrder().getCarId() : driveMeter.getCurrentPriceRequestDto().getCarId();
                String cityCode = driveMeter.getRule().getKeyRule().getCityCode();
                unit = TimeMeter.TimePriceUnit.instanceBySettlement(driveMeter.getChargingCategoryEnum(), driveMeter.getRequestTask(), carId, cityCode);
                break;
        }
        return unit;
    }
}
