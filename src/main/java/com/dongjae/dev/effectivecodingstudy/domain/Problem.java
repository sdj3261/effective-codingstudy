package com.dongjae.dev.effectivecodingstudy.domain;

import com.dongjae.dev.effectivecodingstudy.common.Const;
import com.dongjae.dev.effectivecodingstudy.common.model.BaseEntity;
import jakarta.persistence.*;

public class Problem extends BaseEntity {
    @EmbeddedId
    private ProblemId id;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private Const.ProblemType type; // Const 클래스의 내부 열거형 사용

    @ManyToOne
    private User user;

    public Problem(ProblemId problemId) {
        this.id = problemId;
    }
    public static Problem create() {
        return new Problem(ProblemId.generate());
    }
}
