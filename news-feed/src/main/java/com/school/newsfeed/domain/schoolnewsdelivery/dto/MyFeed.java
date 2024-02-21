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
    private String SchoolName;
    private UUID schoolNewId;
    private String SchoolNewsTitle;
    private String SchoolNewsContents;

    @QueryProjection
    public MyFeed(UUID deliveryId, LocalDateTime deliveryCreatedDate, UUID schoolId,
                  String schoolName, UUID schoolNewId,
                  String schoolNewsTitle, String schoolNewsContents) {
        this.deliveryId = deliveryId;
        this.deliveryCreatedDate = deliveryCreatedDate;
        this.schoolId = schoolId;
        SchoolName = schoolName;
        this.schoolNewId = schoolNewId;
        SchoolNewsTitle = schoolNewsTitle;
    }
}
