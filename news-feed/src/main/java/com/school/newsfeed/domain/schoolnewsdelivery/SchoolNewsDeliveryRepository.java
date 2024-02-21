package com.school.newsfeed.domain.schoolnewsdelivery;

import com.school.newsfeed.domain.schoolnews.SchoolNews;
import com.school.newsfeed.domain.schoolnewsdelivery.repository.SchoolNewsDeliveryRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SchoolNewsDeliveryRepository extends JpaRepository<SchoolNewsDelivery, UUID>, SchoolNewsDeliveryRepositoryCustom {
}
