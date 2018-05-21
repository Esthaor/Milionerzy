package com.grzegorzm.wpam.milionerzy.logic;

import com.grzegorzm.wpam.milionerzy.activities.MenuActivity;
import com.grzegorzm.wpam.milionerzy.model.entity.Question;

import java.util.List;
import java.util.Random;

public class GameSingleton {
    public static final int thresholds[] = {500, 1000, 2000, 5000, 10000, 20000, 40000,
            75000, 125000, 250000, 500000, 1000000};
    private static GameSingleton instance;
    private QuestionAsked lastQuestion;
    private int lastLevel;
    private Random r;
    private int totalPoints;

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
        generateNextQuestion();
    }

    public boolean answer(String answer) {
        boolean res = lastQuestion.answerQuestion(answer);
        if (res) {
            lastLevel++;
            if (lastLevel != thresholds.length){
                totalPoints += lastQuestion.questionPoints();
                generateNextQuestion();
            }
            else
                lastQuestion = null;
        }
        return res;
    }

    public QuestionAsked getLastQuestion() {
        return lastQuestion;
    }
}
