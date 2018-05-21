package com.grzegorzm.wpam.milionerzy.model.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class QuestionPopulator {
    private static SQLiteDatabase db;
    private static final String[][] questions = {
            {"Zwarta grupa zawodników jadących w wyścigu kolarskim to:", "500", "peleton", "aktyw", "konwój", "plejada"}
    };

    public static void createDatabase(SQLiteDatabase db) {
        QuestionPopulator.db = db;
        generateQuestions();
    }

    private static void generateQuestions() {
        for (String[] q : questions) {
            insertQuestion(q[0], Integer.parseInt(q[1]), q[2], q[3], q[4], q[5]);
        }
    }

    private static long insertQuestion(String text, Integer threshold, String correctAnswer, String possibleAnswer0,
                                       String possibleAnswer1, String possibleAnswer2) {
        ContentValues newQuestionValues = new ContentValues();
        newQuestionValues.put(QuestionDbAdapter.KEY_THRESHOLD, threshold);
        newQuestionValues.put(QuestionDbAdapter.KEY_QUESTION_TEXT, text);
        newQuestionValues.put(QuestionDbAdapter.KEY_CORRECT_ANSWER, correctAnswer);
        newQuestionValues.put(QuestionDbAdapter.KEY_POSSIBLE_ANSWER_0, possibleAnswer0);
        newQuestionValues.put(QuestionDbAdapter.KEY_POSSIBLE_ANSWER_1, possibleAnswer1);
        newQuestionValues.put(QuestionDbAdapter.KEY_POSSIBLE_ANSWER_2, possibleAnswer2);
        return db.insert(QuestionDbAdapter.DB_QUESTION_TABLE, null, newQuestionValues);
    }
}
