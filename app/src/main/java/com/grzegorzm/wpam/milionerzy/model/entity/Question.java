package com.grzegorzm.wpam.milionerzy.model.entity;

public class Question {
    private int id;
    private Integer threshold;
    private String questionText;
    private String correctAnswer;
    private String possibleAnswer0;
    private String possibleAnswer1;
    private String possibleAnswer2;

    public Question(int id, Integer threshold, String questionText, String correctAnswer,
                    String possibleAnswer0, String possibleAnswer1, String possibleAnswer2) {
        this.id = id;
        this.threshold = threshold;
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
        this.possibleAnswer0 = possibleAnswer0;
        this.possibleAnswer1 = possibleAnswer1;
        this.possibleAnswer2 = possibleAnswer2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getThreshold() {
        return threshold;
    }

    public void setThreshold(Integer threshold) {
        this.threshold = threshold;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getPossibleAnswer0() {
        return possibleAnswer0;
    }

    public void setPossibleAnswer0(String possibleAnswer0) {
        this.possibleAnswer0 = possibleAnswer0;
    }

    public String getPossibleAnswer1() {
        return possibleAnswer1;
    }

    public void setPossibleAnswer1(String possibleAnswer1) {
        this.possibleAnswer1 = possibleAnswer1;
    }

    public String getPossibleAnswer2() {
        return possibleAnswer2;
    }

    public void setPossibleAnswer2(String possibleAnswer2) {
        this.possibleAnswer2 = possibleAnswer2;
    }
}
