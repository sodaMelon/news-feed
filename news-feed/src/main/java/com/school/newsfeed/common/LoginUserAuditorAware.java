package com.school.newsfeed.common;

import com.school.newsfeed.domain.user.login.dto.LoginUserDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;
import java.util.UUID;

@Configuration
@RequiredArgsConstructor
public class LoginUserAuditorAware implements AuditorAware<UUID> {

    private final HttpSession httpSession;
    @Override
    public Optional<UUID> getCurrentAuditor() {
        LoginUserDto loginUser =  (LoginUserDto) httpSession.getAttribute("loginUser");
        return (loginUser == null) ? Optional.empty() : Optional.ofNullable(loginUser.getUserId());
    }
}
