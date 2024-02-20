package com.school.newsfeed.domain.user;

import com.school.newsfeed.domain.user.dto.UserJoinRequest;
import com.school.newsfeed.domain.user.dto.UserJoinResponse;
import com.school.newsfeed.domain.user.login.UserService;
import com.school.newsfeed.domain.user.login.dto.LoginUserDto;
import jakarta.servlet.http.HttpSession;
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
        if (response.getExistBefore()) {
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/login")
    public ResponseEntity login(@RequestParam String email, HttpSession session){
        LoginUserDto dto = userService.findUserByEmail(email);
        if (dto!= null){
            session.setAttribute("loginUser", dto);
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/logout")
    public ResponseEntity logout(HttpSession session) {
        session.removeAttribute("loginUser");
        return ResponseEntity.noContent().build();
    }
}
