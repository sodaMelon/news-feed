package com.school.newsfeed.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserJoinResponse {
    private String email;
    private Boolean existBefore;
}
