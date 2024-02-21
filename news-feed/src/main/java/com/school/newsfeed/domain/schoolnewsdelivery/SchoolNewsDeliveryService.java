package com.school.newsfeed.domain.schoolnewsdelivery;

import com.school.newsfeed.domain.schoolnewsdelivery.dto.MyFeed;
import com.school.newsfeed.domain.schoolnewsdelivery.repository.SchoolNewsDeliveryRepository;
import com.school.newsfeed.domain.user.dto.LoginUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchoolNewsDeliveryService {
    private final SchoolNewsDeliveryRepository deliveryRepository;
    public List<MyFeed> getMyFeed(LoginUserDto user){
        List<MyFeed> result = deliveryRepository.getFeed(user.getUserId());
        return result;
    }
}
