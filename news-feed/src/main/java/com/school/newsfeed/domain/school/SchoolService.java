package com.school.newsfeed.domain.school;

import com.school.newsfeed.domain.school.dto.MakeSchoolDto;
import com.school.newsfeed.domain.schoolmanager.SchoolManager;
import com.school.newsfeed.domain.schoolmanager.SchoolMangerRepository;
import com.school.newsfeed.domain.user.login.dto.LoginUserDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SchoolService {
    private final SchoolRepository schoolRepository;
    private final SchoolMangerRepository schoolMangerRepository;

    /**
     * 학교 최초 생성 ( 생성 시, 생성한 유저가 학교 관리자 권한도 갖도록함)
     */
    @Transactional
    public School makeNewSchool(MakeSchoolDto schoolDto, LoginUserDto userDto) {
        School school = schoolRepository.save(new School(schoolDto));
        schoolMangerRepository.save(new SchoolManager(school, userDto.getUserId()));
        return school;
    }
}
