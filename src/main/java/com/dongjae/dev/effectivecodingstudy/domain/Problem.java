package com.dongjae.dev.effectivecodingstudy.domain;

import static com.dongjae.dev.effectivecodingstudy.common.enums.Const.ProblemType;

import com.dongjae.dev.effectivecodingstudy.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "problems")
@Getter
public class Problem extends BaseEntity {
    @EmbeddedId
    private ProblemId id;
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private ProblemType type; // SQL, Algorithm
    private Long difficulty;
    private String tag;
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
