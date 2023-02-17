package com.gyh.serviceverificationcode.service.impl;

import com.gyh.internalcommon.constant.CommonStatusEnum;
import com.gyh.internalcommon.dto.ResponseResult;
import com.gyh.internalcommon.dto.serviceverificationcode.response.VerifyCodeResponse;
import com.gyh.internalcommon.util.RedisKeyUtil;
import com.gyh.serviceverificationcode.service.VerifyCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.apache.commons.lang.StringUtils;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class VerifyCodeServiceImpl implements VerifyCodeService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 估算线程数
     * 16核 樱爱开几个线程
     * 线程数 = CPU 可用数 / 1-阻塞系数（io密集型接近1，计算密集型接近0）
     *
     * 提升QPS
     * 提高并发数
     * 减少响应时间
     * */

    /**
     * 生成验证码
     * @param identity
     * @param phoneNumber
     * */
    @Override
    public ResponseResult<VerifyCodeResponse> generate(int identity, String phoneNumber) {

        String code = String.valueOf((int) (Math.random() * 9 + 1) * Math.pow(10,5));
        // 生成Redis的Key
        String keyPre = RedisKeyUtil.generateKeyPreByIdentity(identity);
        String key = keyPre + phoneNumber;
        BoundValueOperations<String, String> codeRedis = redisTemplate.boundValueOps(key);
        codeRedis.set(code, 120, TimeUnit.MINUTES);
        VerifyCodeResponse result = new VerifyCodeResponse();
        result.setCode(code);
        return ResponseResult.success(result);
    }

    @Override
    public ResponseResult verify(int identity, String phoneNumber, String code) {
        // 三挡验证
        // 生成 redis key
        String keyPre = RedisKeyUtil.generateKeyPreByIdentity(identity);
        String key = keyPre + phoneNumber;
        BoundValueOperations<String, String> codeRedis = redisTemplate.boundValueOps(key);
        String redisCode = codeRedis.get();
        if (StringUtils.isNotBlank(code)
            && StringUtils.isNotBlank(redisCode)
            && code.trim().equals(redisCode.trim())) {
            return ResponseResult.success("成功");
        } else {
            return ResponseResult.fail(CommonStatusEnum.VERIFY_CODE_ERROR.getCode(), CommonStatusEnum.VERIFY_CODE_ERROR.getValue());
        }
    }

    /**
     * 判断此手机号发送时限限制
     * @param phoneNumber
     * @return
     * */

    private ResponseResult checkSendCodeTimeLimit(String phoneNumber) {
        // 判断是否有限制 限制1分钟，10分钟，24小时
        return ResponseResult.success("");
    }

    /**
     * 三挡验证校验
     * @param phoneNumber
     * @param code
     * @return
     * */
    private ResponseResult checkCodeThreeLimit(String phoneNumber, String code) {
        // 看流程图
        return ResponseResult.success("");
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5000; i++) {
             String code = String.valueOf(new Random().nextInt(1000000));
             if (code.length() < 6) {
                 System.out.println("有小于6位长的数");
             }
        }
    }
}
