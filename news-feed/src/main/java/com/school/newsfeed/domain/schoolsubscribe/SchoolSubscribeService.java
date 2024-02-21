package com.school.newsfeed.domain.schoolsubscribe;

import com.school.newsfeed.domain.user.dto.LoginUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    public List<SchoolSubscribe> findAllByUserId(LoginUserDto user) {
        return schoolSubscribeRepository.findAllByUserIdAndDelOrderByCreatedDateDesc(user.getUserId(), false);
    }

    public void delete(String schoolId, String schoolSubscribedId, UUID userId) {
        Optional<SchoolSubscribe> result = schoolSubscribeRepository.findByIdAndUserIdAndSchoolIdAndDel
                (UUID.fromString(schoolSubscribedId), userId, UUID.fromString(schoolId),false);
        if (!result.isEmpty()){
            result.get().delete();
            schoolSubscribeRepository.save(result.get());
        }
    }
}
