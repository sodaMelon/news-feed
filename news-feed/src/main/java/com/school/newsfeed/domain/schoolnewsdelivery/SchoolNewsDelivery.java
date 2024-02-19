package com.school.newsfeed.domain.schoolnewsdelivery;

import com.school.newsfeed.common.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

/**
 * After SchoolNews's generation, SchoolNewsHistory should publish to user (by @EventListener)
 * */
@Entity
@Getter
@NoArgsConstructor
public class SchoolNewsDelivery extends BaseTimeEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(name="school_news_delivery_id",columnDefinition = "BINARY(16)")
    private UUID id;

    @Column
    private UUID schoolNewsId;

    @Column
    private UUID schoolId;

    @Column
    private UUID userId; //who subscribed that school

    @Column
    private Boolean del; //default=false
}
