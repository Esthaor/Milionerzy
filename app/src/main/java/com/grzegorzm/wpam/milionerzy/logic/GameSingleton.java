package com.grzegorzm.wpam.milionerzy.logic;

import com.grzegorzm.wpam.milionerzy.activities.MenuActivity;
import com.grzegorzm.wpam.milionerzy.model.entity.Question;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class GameSingleton {
    public static final int thresholds[] = {500, 1000, 2000, 5000, 10000, 20000, 40000,
            75000, 125000, 250000, 500000, 1000000};
    private static GameSingleton instance;
    private QuestionAsked lastQuestion;
    private int lastLevel;
    private Random r;
    private int totalPoints;
    private boolean fiftyFiftyUnused;
    private boolean audienceUnused;
    private boolean telephoneCallUnused;

    private GameSingleton() {
        r = new Random();
    }

    public static GameSingleton getInstance() {
        if (instance == null)
            instance = new GameSingleton();
        return instance;
    }

    private void generateNextQuestion() {
        List<Question> questions = MenuActivity.dbAdapter.getAllQuestionFromThreshold(thresholds[lastLevel]);
        int i = r.nextInt(questions.size());
        Question q = questions.get(i);
        lastQuestion = new QuestionAsked(q);
    }

    public void startGame() {
        lastLevel = 0;
        totalPoints = 0;
        fiftyFiftyUnused = true;
        audienceUnused = true;
        telephoneCallUnused = true;
        generateNextQuestion();
    }

    public boolean answer(String answer) {
        boolean res = lastQuestion.answerQuestion(answer);
        if (res) {
            lastLevel++;
            if (lastLevel != thresholds.length) {
                totalPoints += lastQuestion.questionPoints();
                generateNextQuestion();
            } else
                lastQuestion = null;
        } else
            lastQuestion = null;
        return res;
    }

    public void fiftyFifty() {
        fiftyFiftyUnused = false;
        lastQuestion.fiftyFifty();
    }

    public void telephoneCall() {
        telephoneCallUnused = false;
        lastQuestion.telephoneCall();
    }

    public void audience() {
        audienceUnused = false;
        lastQuestion.audience();
    }

    public QuestionAsked getLastQuestion() {
        return lastQuestion;
    }

    public int getLastLevel() {
        return lastLevel;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public boolean isFiftyFiftyUnused() {
        return fiftyFiftyUnused;
    }

    public boolean isAudienceUnused() {
        return audienceUnused;
    }

    public boolean isTelephoneCallUnused() {
        return telephoneCallUnused;
    }
}
