package com.dongjae.dev.effectivecodingstudy.domain;

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
public class User {
    @EmbeddedId
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UserId userId;
    private String username;
    private String password;
    private String name;
    private String refreshToken;
    private String provider;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public void updateRefreshToken(String newRefreshToken) {
        this.refreshToken = newRefreshToken;
    }
}
