package com.school.newsfeed.domain.schoolsubscribe;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SchoolSubscribeRepository extends JpaRepository<SchoolSubscribe, UUID> {
    SchoolSubscribe findByUserIdAndSchoolIdAndDel(UUID userId,UUID schoolId, boolean del);
}
