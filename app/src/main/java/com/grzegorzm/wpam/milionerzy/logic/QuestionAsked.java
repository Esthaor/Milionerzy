package com.grzegorzm.wpam.milionerzy.logic;

import com.grzegorzm.wpam.milionerzy.model.entity.Question;

public class QuestionAsked {
    private final Question question;
    private Integer answerLeft; // if lifebuoy was used

    public QuestionAsked(Question question) {
        this.question = question;
        this.answerLeft = null;
    }
}
