package com.dongjae.dev.effectivecodingstudy.domain;

import static com.dongjae.dev.effectivecodingstudy.common.Const.ProblemType;
import com.dongjae.dev.effectivecodingstudy.common.Const;
import com.dongjae.dev.effectivecodingstudy.common.model.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "problems")
public class Problem extends BaseEntity {
    @EmbeddedId
    private ProblemId id;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private ProblemType type; // Const 클래스의 내부 열거형 사용

    @ManyToOne
    private User user;

    public Problem(ProblemId problemId) {
        this.id = problemId;
    }

    public Problem() {

    }

    public static Problem create() {
        return new Problem(ProblemId.generate());
    }
}
