package com.school.newsfeed.domain.user;

import com.school.newsfeed.common.BaseTimeEntity;
import com.school.newsfeed.domain.user.dto.UserJoinRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;


import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "sn_user")
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

    public User(UserJoinRequest dto) {
        this.email = dto.getEmail();
        this.name = dto.getName();
        this.phone = dto.getPhone();
        this.userType = dto.getUserType();
        this.userStatus = UserStatus.ACTIVE;
    }
}
