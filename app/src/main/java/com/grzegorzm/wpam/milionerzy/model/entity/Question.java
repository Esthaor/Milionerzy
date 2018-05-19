package com.grzegorzm.wpam.milionerzy.model.entity;

public class Question {
    private int questionId;
    private String questionText;

    public Question() {
    }

    public Question(String questionText) {
        this.questionText = questionText;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }
}
