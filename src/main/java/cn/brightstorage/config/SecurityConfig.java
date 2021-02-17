package cn.brightstorage.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "bright-storage.security")
public class SecurityConfig {

    public static final String SMS_CODE_PREFIX = "SMSCode:";
    public static final String CAPTCHA_PREFIX = "captcha:";
    public static final String REDIS_TOKEN_PREFIX = "accessToken:";
    /**
     * jwt签名密钥
     */
    private String secret;

    /**
     * AES加密密钥
     */
    private String AESKey;

    /**
     * jwt过期时间（小时）
     */
    private int tokenExpireTime;

    /**
     * jwt有效期小于该时间则续期（小时）
     */
    private int tokenRefreshTime;

}
