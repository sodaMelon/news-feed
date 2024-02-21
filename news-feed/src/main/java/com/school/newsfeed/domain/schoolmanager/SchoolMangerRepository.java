package com.school.newsfeed.domain.schoolmanager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SchoolMangerRepository extends JpaRepository<SchoolManager, UUID> {
    List<SchoolManager> findAllBySchoolIdAndDel(UUID schoolId, Boolean Del);
}
