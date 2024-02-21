package com.school.newsfeed.domain.schoolnews;

import com.school.newsfeed.domain.schoolmanager.SchoolManager;
import com.school.newsfeed.domain.schoolmanager.SchoolMangerRepository;
import com.school.newsfeed.domain.schoolnews.dto.MakeSchoolNewsDto;
import com.school.newsfeed.domain.user.dto.LoginUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SchoolNewService {
    private final SchoolMangerRepository schoolManagerRepository;
    private final SchoolNewsRepository schoolNewsRepository;

    private boolean isManagerInSchool(UUID schoolId, LoginUserDto user) {
        List<SchoolManager> managers = schoolManagerRepository.findAllBySchoolIdAndDel(schoolId, false);
        for (SchoolManager s : managers) {
            if (s.getUserId().equals(user.getUserId())) {
                return true;
            }
        }
        return false;
    }

    public SchoolNews makeNewNews(MakeSchoolNewsDto dto, LoginUserDto user) {
        SchoolNews result = null;
        if (isManagerInSchool(dto.getSchoolId(), user)) {
            return schoolNewsRepository.save(new SchoolNews(dto));
            //todo 학교 구독자들에게 SchoolNewsHistory발행
        }
        return result;
    }

    public void update(String schoolNewsId, MakeSchoolNewsDto dto, LoginUserDto user) {
        if (isManagerInSchool(dto.getSchoolId(), user)) {
            Optional<SchoolNews> news = schoolNewsRepository.findById(UUID.fromString(schoolNewsId));
            if (!news.isEmpty()) {
                news.get().update(dto);
                schoolNewsRepository.save(news.get());
                System.out.println(news.get().getTitle());
            }
        }
    }
}
