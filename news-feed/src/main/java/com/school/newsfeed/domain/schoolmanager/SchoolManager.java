package com.school.newsfeed.domain.schoolmanager;

import com.school.newsfeed.common.BaseEntity;
import com.school.newsfeed.domain.school.Area;
import com.school.newsfeed.domain.school.SchoolType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class SchoolManager extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(name="school_manager_id",columnDefinition = "BINARY(16)")
    private UUID id;

    @Column
    private UUID userId;

    @Column
    private UUID schoolId;

    @Column
    private Boolean del; //default=false
}
