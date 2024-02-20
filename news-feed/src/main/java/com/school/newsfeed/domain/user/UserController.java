package com.school.newsfeed.domain.user;

import com.school.newsfeed.domain.user.dto.UserJoinRequest;
import com.school.newsfeed.domain.user.dto.UserJoinResponse;
import com.school.newsfeed.domain.user.login.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity join(@RequestBody UserJoinRequest request){
        UserJoinResponse response =userService.madeNewUser(request);
        System.out.println("hi");
        if (response.getExistBefore()) {
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }
}
