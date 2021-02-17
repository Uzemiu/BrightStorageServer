package cn.brightstorage.filter;

import cn.brightstorage.config.SecurityConfig;
import cn.brightstorage.model.entity.User;
import cn.brightstorage.service.UserService;
import cn.brightstorage.utils.RedisUtil;
import cn.brightstorage.utils.SecurityUtil;
import cn.brightstorage.utils.TokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static cn.brightstorage.config.SecurityConfig.REDIS_TOKEN_PREFIX;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private final TokenUtil tokenUtil;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = TokenUtil.resolveToken(httpServletRequest);
        if(token != null){
            String userId = tokenUtil.verifyToken(token);
            if(userId != null){
                Optional<User> user = userService.getById(userId);
                if(!user.isPresent()){
                    httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,"User not found");
                    return;
                }
                SecurityUtil.setCurrentUser(user.get());
                tokenUtil.checkRenew(token);
            }
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }

}
