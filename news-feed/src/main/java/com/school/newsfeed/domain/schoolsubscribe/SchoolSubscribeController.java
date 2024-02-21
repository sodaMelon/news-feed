package com.school.newsfeed.domain.schoolsubscribe;

import com.school.newsfeed.domain.user.dto.LoginUserDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/school-subscribe")
public class SchoolSubscribeController {
    private final SchoolSubscribeService schoolSubscribeService;

    @PostMapping("/school/{schoolId}")
    public ResponseEntity makeNewSchoolSubscribe(@PathVariable("schoolId") String schoolId, HttpSession session) {
        LoginUserDto user = (LoginUserDto) session.getAttribute("loginUser");
        SchoolSubscribe result = schoolSubscribeService.makeNewSubscribe(schoolId, user);
        return result != null ? ResponseEntity.ok(result) : ResponseEntity.badRequest().build();
    }
}
