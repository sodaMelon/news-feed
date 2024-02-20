package com.school.newsfeed.domain.user.login;

import com.school.newsfeed.domain.user.login.dto.LoginUserDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * temporary login(by session)
 */
@RestController
@RequiredArgsConstructor
public class LoginController {
    private final UserService loginService;

    @GetMapping("/login")
    public ResponseEntity login(@RequestParam String email, HttpSession session){
        LoginUserDto dto = loginService.findUserByEmail(email);
        if (dto!= null){
            session.setAttribute("loginUser", dto);
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.badRequest().body("login fail");
    }

    @GetMapping("/logout")
    public ResponseEntity logout(HttpSession session) {
        session.removeAttribute("loginUser");
        return ResponseEntity.ok().body("logout complete");
    }
}
