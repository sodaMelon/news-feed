package com.school.newsfeed.domain.schoolnewsdelivery;

import com.school.newsfeed.domain.schoolnewsdelivery.dto.MyFeed;
import com.school.newsfeed.domain.user.dto.LoginUserDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/school-news-delivery")
public class SchoolNewDeliveryController {
    private final SchoolNewsDeliveryService deliveryService;

    @GetMapping("/my-news-feed")
    public ResponseEntity getMyFeed(HttpSession session){
        LoginUserDto user = (LoginUserDto) session.getAttribute("loginUser");
        if (user==null) return ResponseEntity.badRequest().build();
        List<MyFeed> result = deliveryService.getMyFeed(user);
        return ResponseEntity.ok(result);
    }
}
