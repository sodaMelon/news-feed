package com.school.newsfeed.domain.school;

import com.school.newsfeed.domain.school.dto.MakeSchoolDto;
import com.school.newsfeed.domain.user.UserType;
import com.school.newsfeed.domain.user.dto.LoginUserDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SchoolController {
    private final  SchoolService schoolService;

    /**
     * 기존에 같은 이름의 학교가 있는지 확인 후, 사용 권장
     * */
    @PostMapping("/school")
    public ResponseEntity makeNewSchool(@RequestBody MakeSchoolDto dto, HttpSession session){
        LoginUserDto user= (LoginUserDto) session.getAttribute("loginUser");
        if (user.getUserType()!= UserType.SCHOOL_MANAGER) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        School school = schoolService.makeNewSchool(dto,user);
        return ResponseEntity.ok(school);
    }

}
