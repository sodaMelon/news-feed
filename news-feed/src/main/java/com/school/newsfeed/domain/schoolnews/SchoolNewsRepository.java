package com.school.newsfeed.domain.schoolnews;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface SchoolNewsRepository extends JpaRepository<SchoolNews, UUID> {
}
