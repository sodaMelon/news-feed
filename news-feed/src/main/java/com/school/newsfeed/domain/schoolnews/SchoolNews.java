package com.school.newsfeed.domain.schoolnews;

import com.school.newsfeed.common.BaseEntity;
import com.school.newsfeed.domain.schoolnews.dto.MakeSchoolNewsDto;
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
public class SchoolNews extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(name="school_news_id",columnDefinition = "BINARY(16)")
    private UUID id;

    @Column
    private UUID schoolId;

    @Column
    private Boolean del; //default=false

    @Column
    private String title;

    @Column
    private String content;

    public SchoolNews(MakeSchoolNewsDto dto) {
        this.schoolId = dto.getSchoolId();
        this.del = false;
        this.title = dto.getTitle();
        this.content = dto.getContent();
    }

    public void update(MakeSchoolNewsDto dto) {
        this.schoolId = dto.getSchoolId();
        this.title = dto.getTitle();
        this.content = dto.getContent();
    }

    public void delete(){
        this.del=true;
    }
}
