package com.school.newsfeed.domain.schoolnewsdelivery.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.school.newsfeed.domain.schoolnewsdelivery.dto.MyFeed;

import com.school.newsfeed.domain.schoolnewsdelivery.dto.QMyFeed;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

import static com.school.newsfeed.domain.school.QSchool.school;
import static com.school.newsfeed.domain.schoolnews.QSchoolNews.schoolNews;
import static com.school.newsfeed.domain.schoolnewsdelivery.QSchoolNewsDelivery.schoolNewsDelivery;


@RequiredArgsConstructor
public class SchoolNewsDeliveryRepositoryImpl implements SchoolNewsDeliveryRepositoryCustom {
    private  final JPAQueryFactory queryFactory;

    @Override
    public List<MyFeed> getFeed(UUID userId) {
        UUID userid = UUID.fromString(userId.toString()); //알수 없는 이유로 빨간줄 떠서 일단 이렇게 처리
        List<MyFeed> result = queryFactory.select(new QMyFeed(schoolNewsDelivery.id, schoolNewsDelivery.createdDate,
                        schoolNewsDelivery.schoolId, school.name,
                        schoolNewsDelivery.schoolNewsId, schoolNews.title, schoolNews.content))
                .from(schoolNewsDelivery)
                .where(schoolNewsDelivery.userId.eq(userid))
                .leftJoin(school)
                .on(schoolNewsDelivery.schoolId.eq(school.id))
                .leftJoin(schoolNews)
                .on(schoolNewsDelivery.schoolNewsId.eq(schoolNews.id))
                .orderBy(schoolNewsDelivery.createdDate.desc())
                .fetch();
        return result;
    }
}
