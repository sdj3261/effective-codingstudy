package com.dongjae.dev.effectivecodingstudy.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public class ProblemId extends EntityId{
    public ProblemId(String value) {
        super(value);
    }

    public ProblemId() {
        super();
    }

    public static ProblemId generate() {
        return new ProblemId(newTsid());
    }

    public static ProblemId of(String problemId) {
        return new ProblemId(problemId);
    }
}

