package com.grzegorzm.wpam.milionerzy.logic;

import com.grzegorzm.wpam.milionerzy.model.entity.Question;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class QuestionAsked {
    private final Question question;
    private final String questionText;
    private final String correctAnswer;
    private List<String> answers;
    private Date startTime;
    private Date answerTime;

    public QuestionAsked(Question question) {
        this.question = question;
        this.correctAnswer = question.getCorrectAnswer();
        this.questionText = question.getQuestionText();
        this.answers = new ArrayList<>();
        answers.add(question.getCorrectAnswer());
        answers.add(question.getPossibleAnswer0());
        answers.add(question.getPossibleAnswer1());
        answers.add(question.getPossibleAnswer2());
        Collections.shuffle(answers);
        startTime = new Date();
    }

    public int questionPoints() {
        // TODO[3]: create point system based on start and answer time
        return 100;
    }

    public boolean answerQuestion(String answer) {
        answerTime = new Date();
        return answer.equals(correctAnswer);
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }
}
