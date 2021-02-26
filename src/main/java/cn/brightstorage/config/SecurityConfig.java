package cn.brightstorage.config;

import lombok.Data;

@Data
public class SecurityConfig {

    public static final String SMS_CODE_PREFIX = "SMSCode:";
    public static final String CAPTCHA_PREFIX = "captcha:";
    public static final String REDIS_TOKEN_PREFIX = "accessToken:";
    /**
     * token签名密钥
     */
    public static final String SECRET = "j.2_d?+bn";

    /**
     * AES加密密钥
     */
    public static final  String AES_KEY = "I8KI6i+3UhXVd02N";

    /**
     * token过期时间（小时）
     */
    public static final int TOKEN_EXPIRE_TIME = 24;

    /**
     * token有效期小于该时间则续期（小时）
     */
    public static final int TOKEN_REFRESH_TIME = 2;

}
