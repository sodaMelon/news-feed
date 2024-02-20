package com.school.newsfeed.domain.user.login;

import com.school.newsfeed.domain.school.School;
import com.school.newsfeed.domain.school.SchoolRepository;
import com.school.newsfeed.domain.schoolmanager.SchoolMangerRepository;
import com.school.newsfeed.domain.user.User;
import com.school.newsfeed.domain.user.UserRepository;
import com.school.newsfeed.domain.user.dto.UserJoinRequest;
import com.school.newsfeed.domain.user.dto.UserJoinResponse;
import com.school.newsfeed.domain.user.login.dto.LoginUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public LoginUserDto findUserByEmail(String email){
        Optional<User> user= userRepository.findByEmail(email);
        LoginUserDto userDto = user.isEmpty()? null : new LoginUserDto(user.get());
        return userDto;
    }

    public UserJoinResponse madeNewUser(UserJoinRequest request){
        Optional<User> result = userRepository.findByEmail(request.getEmail());
        System.out.println("here");
                if (result.isEmpty() ) {
                   userRepository.save(new User(request));
                   return new UserJoinResponse(request.getEmail(), false);
                }
        return new UserJoinResponse(request.getEmail(), true);
    }
}
