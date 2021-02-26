package cn.brightstorage.utils;

import cn.brightstorage.config.SecurityConfig;
import cn.brightstorage.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static cn.brightstorage.config.SecurityConfig.REDIS_TOKEN_PREFIX;

@Component
@RequiredArgsConstructor
public class TokenUtil{

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String AUTHORIZATION = "Authorization";

    private final RedisUtil redisUtil;

    public String generateAndSetToken(User user) {
        String key = System.currentTimeMillis() + user.getId();
        String token = UUID.nameUUIDFromBytes(key.getBytes()).toString();
        redisUtil.set(REDIS_TOKEN_PREFIX + token, user.getId(),
                SecurityConfig.TOKEN_EXPIRE_TIME, TimeUnit.HOURS);
        return token;
    }

    /**
     *
     * @param token /
     * @return userId
     */
    public String verifyToken(String token){
        return (String) redisUtil.get(REDIS_TOKEN_PREFIX + token);
    }

    public void checkRenew(String token){
        String redisKey = REDIS_TOKEN_PREFIX + token;
        long expire = redisUtil.getExpire(redisKey);
        if(expire < SecurityConfig.TOKEN_REFRESH_TIME * 3600_000){
            redisUtil.expire(redisKey, SecurityConfig.TOKEN_EXPIRE_TIME, TimeUnit.HOURS);
        }
    }

    public static String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.replace(TOKEN_PREFIX, "");
        }
        return null;
    }

}
