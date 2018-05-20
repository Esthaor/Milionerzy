package com.grzegorzm.wpam.milionerzy.model.data;

import android.content.Context;

public class QuestionPopulator {

    public static void createDatabase(QuestionDbAdapter dbAdapter) {
        generateQuestions(dbAdapter);
    }

    private static void generateQuestions(QuestionDbAdapter dbAdapter) {
        dbAdapter.insertQuestion("Pytanie", 500, "true",
                "false", "false", "false");
    }
}
