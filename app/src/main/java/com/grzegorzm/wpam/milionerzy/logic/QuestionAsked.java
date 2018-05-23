package com.grzegorzm.wpam.milionerzy.logic;

import com.grzegorzm.wpam.milionerzy.model.entity.Question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class QuestionAsked {
    private final String[] TELEPHONE_GOOD_STARTERS = {"Myślę, że to ", "Wydaje mi się, że chodzi o ",
            "Wiem, że to ", "Chyba ", "Moim zdaniem to ", "Strzelam że chodzi o"};
    private final String[] TELEPHONE_BAD_STARTERS = {"Nie mam pojęcia.", "Nie potrafię Ci pomóc.",
            "Niestety nie wiem."};
    private final Question question;
    private List<String> answers;
    private List<Integer> audiencePercentage;
    private String telephoneMessage;
    private Date startTime;
    private Date answerTime;
    private int level;

    private final static int MAX_BONUS_POINT = 1000;
    private final static int POINTS_PER_LEVEL = 1100;

    public QuestionAsked(Question question) {
        this.question = question;
        this.answers = new ArrayList<>();
        answers.add(question.getCorrectAnswer());
        answers.add(question.getPossibleAnswer0());
        answers.add(question.getPossibleAnswer1());
        answers.add(question.getPossibleAnswer2());
        Collections.shuffle(answers);

        Integer t = question.getThreshold();
        int[] thresholds = GameSingleton.thresholds;
        level = 0;
        for (int i = 0; i < thresholds.length; i++)
            if (t.equals(thresholds[i]))
                level = i;
        startTime = new Date();
        audiencePercentage = null;
        telephoneMessage = null;
    }

    public static QuestionAsked loadQuestion(Question q) {
        QuestionAsked qa = new QuestionAsked(q);
        qa.startTime = new Date(0);
        return qa;
    }

    public void fiftyFifty() {
        List<String> res = new ArrayList<>();
        Random r = new Random();
        res.add(question.getCorrectAnswer());
        answers.remove(question.getCorrectAnswer());
        res.add(answers.get(r.nextInt(3)));
        this.answers = res;
    }

    public void telephoneCall() {
        Random r = new Random();
        int rand = r.nextInt(30) - 30 + level;
        boolean goodAnswer = rand < 0;
        int goodLen = TELEPHONE_GOOD_STARTERS.length;
        int badLen = TELEPHONE_BAD_STARTERS.length;
        if (goodAnswer)
            telephoneMessage = TELEPHONE_GOOD_STARTERS[r.nextInt(goodLen)] + question.getCorrectAnswer() + ".";
        else if (r.nextInt(2) < 1)
            telephoneMessage = TELEPHONE_GOOD_STARTERS[r.nextInt(goodLen)] + answers.get(r.nextInt(4)) + ".";
        else
            telephoneMessage = TELEPHONE_BAD_STARTERS[r.nextInt(badLen)];
    }

    public void audience() {
        Random r = new Random();
        int rand = r.nextInt(20) - 25 + level;
        boolean goodAnswer = rand < 0;
        audiencePercentage = new ArrayList<>();
        int correctPercent;
        int badPercent1;
        int badPercent2;
        int badPercent3;
        if (goodAnswer) {
            correctPercent = r.nextInt(30) + 40;
            badPercent1 = r.nextInt(100 - correctPercent - 20);
            badPercent2 = r.nextInt(20);
            badPercent3 = 100 - correctPercent - badPercent2 - badPercent1;
            if (answers.size() == 2) {
                correctPercent = r.nextInt(30) + 50;
                badPercent1 = badPercent2 = badPercent3 = 100 - correctPercent;
            }
        } else {
            correctPercent = r.nextInt(30);
            badPercent1 = r.nextInt(100 - correctPercent - 20);
            badPercent2 = r.nextInt(20);
            badPercent3 = 100 - correctPercent - badPercent2 - badPercent1;
            if (answers.size() == 2) {
                correctPercent = r.nextInt(40);
                badPercent1 = badPercent2 = badPercent3 = 100 - correctPercent;
            }
        }
        List<Integer> badPercentage = Arrays.asList(badPercent1, badPercent2, badPercent3);
        Collections.shuffle(badPercentage);
        int i = 0;
        for (String s : answers)
            if (s.equals(question.getCorrectAnswer()))
                audiencePercentage.add(correctPercent);
            else
                audiencePercentage.add(badPercentage.get(i++));
    }

    public int questionPoints() {
        int points = POINTS_PER_LEVEL;
        for (int i = 0; i < level; i++)
            points = (points * POINTS_PER_LEVEL) / 1000;

        long d1 = startTime.getTime();
        long d2 = answerTime.getTime();
        long diffInSec = (d2 - d1) / 1000;

        if (diffInSec < 5)
            points += MAX_BONUS_POINT;
        else if (diffInSec < 65)
            points += MAX_BONUS_POINT - ((diffInSec - 5) * (diffInSec - 5)) / 3600;
        return points;
    }

    public boolean answerQuestion(String answer) {
        answerTime = new Date();
        return answer.equals(question.getCorrectAnswer());
    }

    public String getQuestionText() {
        return question.getQuestionText();
    }

    public List<String> getAnswers() {
        return answers;
    }

    public List<Integer> getAudiencePercentage() {
        return audiencePercentage;
    }

    public String getTelephoneMessage() {
        return telephoneMessage;
    }

    public Integer getThreshold() {
        return question.getThreshold();
    }

    public int getCorrectAnswerIndex() {
        return answers.indexOf(question.getCorrectAnswer());
    }

    public int getQuestionId() {
        return question.getId();
    }
}
