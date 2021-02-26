package cn.brightstorage.utils;

import cn.brightstorage.config.SecurityConfig;
import cn.brightstorage.exception.BadRequestException;
import cn.brightstorage.model.support.VerificationCode;
import com.wf.captcha.ArithmeticCaptcha;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static cn.brightstorage.config.SecurityConfig.CAPTCHA_PREFIX;

@Component
@RequiredArgsConstructor
public class VerificationCodeUtil {

    private final RedisUtil redisUtil;

    public VerificationCode generateImageCaptcha(){
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(111,36,2);
        String verCode = captcha.text().toLowerCase();
        if(verCode.contains(".")){
            verCode = verCode.split("\\.")[0];
        }
        String uuid = UUID.randomUUID().toString();
        redisUtil.set(CAPTCHA_PREFIX + uuid, verCode, 2, TimeUnit.MINUTES);
        return new VerificationCode(verCode,captcha.toBase64(), uuid);
    }

    public boolean verify(VerificationCode code){
        String captcha = (String) redisUtil.get(CAPTCHA_PREFIX + code.getUuid());
        redisUtil.del(CAPTCHA_PREFIX + code.getUuid());
        if (Strings.isBlank(captcha)) {
            throw new BadRequestException("验证码不存在或已过期");
        }
        if (Strings.isBlank(code.getCode()) || !code.getCode().equalsIgnoreCase(captcha)) {
            throw new BadRequestException("验证码错误");
        }
        return true;
    }
}
