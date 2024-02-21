package com.school.newsfeed.domain.schoolnews;

import com.school.newsfeed.domain.schoolnews.dto.MakeSchoolNewsDto;
import com.school.newsfeed.domain.user.UserType;
import com.school.newsfeed.domain.user.dto.LoginUserDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/school-news")
public class SchoolNewsController {
    private final  SchoolNewService schoolNewsService;

    @PostMapping
    public ResponseEntity makeNewSchoolNews(@RequestBody MakeSchoolNewsDto dto, HttpSession session){
        LoginUserDto user= (LoginUserDto) session.getAttribute("loginUser");
        if (user.getUserType()!= UserType.SCHOOL_MANAGER) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        SchoolNews news = schoolNewsService.makeNewNews(dto,user);
        return news!=null? ResponseEntity.ok(news):ResponseEntity.badRequest().build();
    }

    @PatchMapping("/{schoolNewsId}")
    public ResponseEntity updateSchoolNews(@PathVariable("schoolNewsId") String schoolNewsId,
                                           @RequestBody MakeSchoolNewsDto dto, HttpSession session){
        LoginUserDto user= (LoginUserDto) session.getAttribute("loginUser");
        if (user.getUserType()!= UserType.SCHOOL_MANAGER) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        schoolNewsService.update(schoolNewsId, dto, user);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{schoolNewsId}")
    public ResponseEntity deleteSchoolNews(@PathVariable("schoolNewsId") String schoolNewsId,
                                           @RequestParam String schoolId,
                                           HttpSession session){
        LoginUserDto user= (LoginUserDto) session.getAttribute("loginUser");
        if (user.getUserType()!= UserType.SCHOOL_MANAGER) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        schoolNewsService.delete(schoolNewsId, schoolId, user);
        return ResponseEntity.noContent().build();
    }
}
