package com.grzegorzm.wpam.milionerzy.model.entity;

public class Save {
    private int id;
    private Question question;
    private int lastLevel;
    private int points;
    private boolean fiftyFiftyUnused;
    private boolean audienceUnused;
    private boolean telephoneUnused;

    public Save(int id, Question question, int lastLevel, int points, boolean fiftyFiftyUnused, boolean audienceUnused, boolean telephoneUnused) {
        this.id = id;
        this.question = question;
        this.lastLevel = lastLevel;
        this.points = points;
        this.fiftyFiftyUnused = fiftyFiftyUnused;
        this.audienceUnused = audienceUnused;
        this.telephoneUnused = telephoneUnused;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public int getLastLevel() {
        return lastLevel;
    }

    public void setLastLevel(int lastLevel) {
        this.lastLevel = lastLevel;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public boolean isFiftyFiftyUnused() {
        return fiftyFiftyUnused;
    }

    public void setFiftyFiftyUnused(boolean fiftyFiftyUnused) {
        this.fiftyFiftyUnused = fiftyFiftyUnused;
    }

    public boolean isAudienceUnused() {
        return audienceUnused;
    }

    public void setAudienceUnused(boolean audienceUnused) {
        this.audienceUnused = audienceUnused;
    }

    public boolean isTelephoneUnused() {
        return telephoneUnused;
    }

    public void setTelephoneUnused(boolean telephoneUnused) {
        this.telephoneUnused = telephoneUnused;
    }
}
