package com.dongjae.dev.effectivecodingstudy.domain;

import com.dongjae.dev.effectivecodingstudy.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@ToString(of = {"username"})
@Getter
@Builder
public class User extends BaseEntity {
    @EmbeddedId
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UserId userId;
    private String username;
    private String password;
    private String name;
    private String provider;

    public User(UserId userId) {
        this.userId = userId;
    }
    public static User create() {
        return new User(UserId.generate());
    }
}
