package com.school.newsfeed.domain.user.login.dto;

import com.school.newsfeed.domain.user.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import com.school.newsfeed.domain.user.User;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserDto implements Serializable {
    private UUID userId;
    private String email;
    private String userName;
    private UserType userType;

    public LoginUserDto(User user) {
        this.userId = user.getId();
        this.email = user.getEmail();
        this.userName = user.getName();
        this.userType = user.getUserType();
    }
}
