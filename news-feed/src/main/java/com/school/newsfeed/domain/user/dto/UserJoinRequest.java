package com.school.newsfeed.domain.user.dto;

import com.school.newsfeed.domain.user.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserJoinRequest {
    private String email;
    private String name;
    private String phone;
    private UserType userType;
}
