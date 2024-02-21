package com.school.newsfeed.domain.schoolnewsdelivery.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class MyFeed {
    private UUID deliveryId;
    private LocalDateTime deliveryCreatedDate;
    private UUID schoolId;
    private String schoolName;
    private UUID schoolNewsId;
    private String schoolNewsTitle;
    private String schoolNewsContent;

    @QueryProjection
    public MyFeed(UUID deliveryId, LocalDateTime deliveryCreatedDate, UUID schoolId,
                  String schoolName, UUID schoolNewId,
                  String schoolNewsTitle, String schoolNewsContent) {
        this.deliveryId = deliveryId;
        this.deliveryCreatedDate = deliveryCreatedDate;
        this.schoolId = schoolId;
        this.schoolName = schoolName;
        this.schoolNewsId = schoolNewId;
        this.schoolNewsTitle = schoolNewsTitle;
        this.schoolNewsContent = schoolNewsContent;
    }
}
