package com.school.newsfeed.domain.schoolmanager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SchoolMangerRepository extends JpaRepository<SchoolManager, UUID> {
}
