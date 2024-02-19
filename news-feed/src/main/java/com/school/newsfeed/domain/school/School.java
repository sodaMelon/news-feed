package com.school.newsfeed.domain.school;

import com.school.newsfeed.common.BaseEntity;
import com.school.newsfeed.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class School extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(name="school_id",columnDefinition = "BINARY(16)")
    private UUID id;

    @Column
    private String name;

    @Column
    private String address;

    @Column
    @Enumerated(EnumType.STRING)
    private SchoolType schoolType;

    @Column
    @Enumerated(EnumType.STRING)
    private Area area;

    @Column
    private Boolean del; //default=false
}
