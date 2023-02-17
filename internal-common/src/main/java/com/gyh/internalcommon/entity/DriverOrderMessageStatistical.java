package com.gyh.internalcommon.entity;

import lombok.Data;

@Data
public class DriverOrderMessageStatistical {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_driver_order_message_statistical.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_driver_order_message_statistical.driver_id
     *
     * @mbggenerated
     */
    private Integer driverId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_driver_order_message_statistical.engine_number
     *
     * @mbggenerated
     */
    private String drivingLicenceNumber;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_driver_order_message_statistical.cycle
     *
     * @mbggenerated
     */
    private String cycle;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_driver_order_message_statistical.order_count
     *
     * @mbggenerated
     */
    private Integer orderCount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_driver_order_message_statistical.traffic_violations_count
     *
     * @mbggenerated
     */
    private Integer trafficViolationsCount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_driver_order_message_statistical.complained_count
     *
     * @mbggenerated
     */
    private Integer complainedCount;


}