package cn.brightstorage.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import static cn.brightstorage.config.SecurityConfig.SMS_CODE_PREFIX;

@Component
@RequiredArgsConstructor
public class SMSUtil {

    private final RedisUtil redisUtil;

    public String sendSMSCode(String phone){
        String code = "";
        redisUtil.set(SMS_CODE_PREFIX + phone, code);
        return code;
    }

    public boolean verifyCode(String phone, String code){
        String trueCode = (String) redisUtil.get(SMS_CODE_PREFIX + phone);
        Assert.notNull(trueCode, "短信验证码错误");
        Assert.isTrue(trueCode.equals(code),"短信验证码错误");
        return true;
    }
}
