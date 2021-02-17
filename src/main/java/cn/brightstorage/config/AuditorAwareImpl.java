package cn.brightstorage.config;

import cn.brightstorage.model.entity.User;
import cn.brightstorage.utils.SecurityUtil;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.ofNullable(SecurityUtil.getCurrentUser()).map(User::getId);
    }
}
