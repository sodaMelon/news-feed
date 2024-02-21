package com.school.newsfeed.domain.schoolsubscribe;

import com.school.newsfeed.domain.user.dto.LoginUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SchoolSubscribeService {
    private final SchoolSubscribeRepository schoolSubscribeRepository;

    public SchoolSubscribe makeNewSubscribe(String schoolId, LoginUserDto user) {
        UUID schoolUuid = UUID.fromString(schoolId);
        SchoolSubscribe result = schoolSubscribeRepository
                .findByUserIdAndSchoolIdAndDel(user.getUserId(), schoolUuid, false);
        if (result == null) {
            result = schoolSubscribeRepository.save(new SchoolSubscribe(schoolUuid, user.getUserId()));
        }
        return result;
    }
}
