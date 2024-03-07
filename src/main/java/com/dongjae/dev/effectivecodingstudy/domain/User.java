package com.dongjae.dev.effectivecodingstudy.domain;

import com.dongjae.dev.effectivecodingstudy.common.enums.Const;
import com.dongjae.dev.effectivecodingstudy.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import static com.dongjae.dev.effectivecodingstudy.common.enums.Const.RoleType;
import static com.dongjae.dev.effectivecodingstudy.common.enums.Const.ProviderType;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@ToString(of = {"email"})
@Getter
@Builder
public class User extends BaseEntity {
    @EmbeddedId
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UserId userId;
    @Column
    private String socialId;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private String name;
    @Column
    private String nickname;
    @Column
    @Enumerated(EnumType.STRING)
    private ProviderType provider;
    @Column
    @Enumerated(EnumType.STRING)
    private RoleType Role;
    @OneToMany(mappedBy = "user")
    private List<Problem> problems;

    public User(UserId userId) {
        this.userId = userId;
    }
    public static User create() {
        return new User(UserId.generate());
    }
}
