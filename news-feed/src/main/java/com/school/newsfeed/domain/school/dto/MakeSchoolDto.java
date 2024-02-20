package com.school.newsfeed.domain.school.dto;

import com.school.newsfeed.domain.school.Area;
import com.school.newsfeed.domain.school.SchoolType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MakeSchoolDto {
    private String name;
    private String address;
    private SchoolType schoolType;
    private Area area;
}
