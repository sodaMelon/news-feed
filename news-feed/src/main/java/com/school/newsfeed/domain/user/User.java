package com.school.newsfeed.domain.user;

import com.school.newsfeed.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;


import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class User  extends BaseTimeEntity {
    @Id @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(name="user_id",columnDefinition = "BINARY(16)")
    private UUID id;

    @Column
    private String name;

    @Column String email;

    @Column
    private String phone;

    @Column
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Column
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    public User(String name, String phone) {
        this.name = name;
        this.phone = phone;
        this.userType = UserType.STUDENT;
        this.userStatus = UserStatus.ACTIVE;
    }
}
