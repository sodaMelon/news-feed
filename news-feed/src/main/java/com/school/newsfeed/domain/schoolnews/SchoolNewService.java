package com.school.newsfeed.domain.schoolnews;

import com.school.newsfeed.domain.schoolmanager.SchoolManager;
import com.school.newsfeed.domain.schoolmanager.SchoolMangerRepository;
import com.school.newsfeed.domain.schoolnews.dto.MakeSchoolNewsDto;
import com.school.newsfeed.domain.schoolnewsdelivery.SchoolNewsDelivery;
import com.school.newsfeed.domain.schoolsubscribe.SchoolSubscribe;
import com.school.newsfeed.domain.schoolsubscribe.SchoolSubscribeRepository;
import com.school.newsfeed.domain.user.dto.LoginUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SchoolNewService {
    private final SchoolMangerRepository schoolManagerRepository;
    private final SchoolNewsRepository schoolNewsRepository;
    private final SchoolSubscribeRepository schoolSubscribeRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public List<SchoolNews> searchSchoolNews(String schoolId, LoginUserDto user){
        return schoolNewsRepository.findAllBySchoolIdAndDel(UUID.fromString(schoolId), false);
    }
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
            result = schoolNewsRepository.save(new SchoolNews(dto));
            applicationEventPublisher.publishEvent(new SchoolNewsDelivery(result));
        }
        return result;
    }

    public void update(String schoolNewsId, MakeSchoolNewsDto dto, LoginUserDto user) {
        if (isManagerInSchool(dto.getSchoolId(), user)) {
            Optional<SchoolNews> news = schoolNewsRepository.findById(UUID.fromString(schoolNewsId));
            if (!news.isEmpty()) {
                news.get().update(dto);
                schoolNewsRepository.save(news.get());
            }
        }
    }

    public void delete(String schoolNewsId, String schoolId, LoginUserDto user) {
        if (isManagerInSchool(UUID.fromString(schoolId), user)) {
            Optional<SchoolNews> news = schoolNewsRepository.findById(UUID.fromString(schoolNewsId));
            if (!news.isEmpty()) {
                news.get().delete();
                schoolNewsRepository.save(news.get());
            }
        }
    }
}
