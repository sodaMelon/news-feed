package com.school.newsfeed.domain.schoolsubscribe;

import com.school.newsfeed.domain.user.dto.LoginUserDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/school/my-school")
    public ResponseEntity searchMySchoolSubscribes(HttpSession session) {
        LoginUserDto user = (LoginUserDto) session.getAttribute("loginUser");
        List<SchoolSubscribe> result = schoolSubscribeService.findAllByUserId(user);
        return !result.isEmpty() ? ResponseEntity.ok(result) : ResponseEntity.noContent().build();
    }

    @DeleteMapping("/school/{schoolId}/{schoolSubscribeId}")
    public ResponseEntity deleteSchoolSubscribe(@PathVariable("schoolId") String schoolId,
                                                @PathVariable("schoolSubscribeId") String schoolSubscribedId,
                                                HttpSession session) {
        LoginUserDto user = (LoginUserDto) session.getAttribute("loginUser");
        schoolSubscribeService.delete(schoolId, schoolSubscribedId, user.getUserId());
        return ResponseEntity.noContent().build();
    }
}
