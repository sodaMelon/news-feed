package com.school.newsfeed.domain.schoolsubscribe;

import com.school.newsfeed.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class SchoolSubscribe extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(name="school_subscribe_id",columnDefinition = "BINARY(16)")
    private UUID id;

    @Column
    private UUID schoolId;

    @Column
    private UUID userId;

    @Column
    private Boolean del; //default=false

    public SchoolSubscribe(UUID schoolId, UUID userId) {
        this.schoolId = schoolId;
        this.userId = userId;
        this.del= false;
    }
}
