package com.school.newsfeed.domain.schoolnewsdelivery.repository;

import com.school.newsfeed.domain.schoolnewsdelivery.dto.MyFeed;

import java.util.List;
import java.util.UUID;

public interface SchoolNewsDeliveryRepositoryCustom {
    List<MyFeed> getFeed(UUID userId);
}
