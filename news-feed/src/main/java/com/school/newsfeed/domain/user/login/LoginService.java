package com.school.newsfeed.domain.user.login;

import com.school.newsfeed.domain.user.User;
import com.school.newsfeed.domain.user.UserRepository;
import com.school.newsfeed.domain.user.login.dto.LoginUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final UserRepository userRepository;

    public LoginUserDto findUserByEmail(String email){
        Optional<User> user= userRepository.findByEmail(email);
        LoginUserDto userDto = user.isEmpty()? null : new LoginUserDto(user.get());
        return userDto;
    }
}
