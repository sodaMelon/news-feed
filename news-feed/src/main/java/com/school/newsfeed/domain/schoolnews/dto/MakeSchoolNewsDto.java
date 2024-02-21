package com.school.newsfeed.domain.schoolnews.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@Getter
@AllArgsConstructor
public class MakeSchoolNewsDto implements Serializable {
    private UUID schoolId;

    private String title;

    private String content;
}
