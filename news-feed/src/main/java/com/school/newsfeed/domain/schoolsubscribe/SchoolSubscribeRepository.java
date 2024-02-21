package com.school.newsfeed.domain.schoolsubscribe;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SchoolSubscribeRepository extends JpaRepository<SchoolSubscribe, UUID> {
    List<SchoolSubscribe> findAllBySchoolIdAndDel(UUID schoolId, boolean del);
    SchoolSubscribe findByUserIdAndSchoolIdAndDel(UUID userId,UUID schoolId, boolean del);
    List<SchoolSubscribe> findAllByUserIdAndDelOrderByCreatedDateDesc(UUID userId, boolean del);
    Optional<SchoolSubscribe> findByIdAndUserIdAndSchoolIdAndDel(UUID id, UUID userId, UUID schoolId, boolean del);
}
