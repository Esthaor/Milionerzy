package com.grzegorzm.wpam.milionerzy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.grzegorzm.wpam.milionerzy.R;
import com.grzegorzm.wpam.milionerzy.logic.GameSingleton;
import com.grzegorzm.wpam.milionerzy.logic.QuestionAsked;

import java.util.List;

public class QuestionActivity extends AppCompatActivity {
    GameSingleton gs = GameSingleton.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        QuestionAsked lastQuestion = gs.getLastQuestion();
        List<String> answers = lastQuestion.getAnswers();

        TextView textView = findViewById(R.id.textView);
        Button buttonAnswer1 = findViewById(R.id.buttonAnswer1);
        Button buttonAnswer2 = findViewById(R.id.buttonAnswer2);
        Button buttonAnswer3 = findViewById(R.id.buttonAnswer3);
        Button buttonAnswer4 = findViewById(R.id.buttonAnswer4);
        textView.setText(lastQuestion.getQuestionText());
        buttonAnswer1.setText(answers.get(0));
        buttonAnswer2.setText(answers.get(1));
        buttonAnswer1.setVisibility(View.VISIBLE);
        buttonAnswer2.setVisibility(View.VISIBLE);
        if (answers.size() > 2) {
            buttonAnswer3.setText(answers.get(2));
            buttonAnswer4.setText(answers.get(3));
            buttonAnswer3.setVisibility(View.VISIBLE);
            buttonAnswer4.setVisibility(View.VISIBLE);
        } else {
            buttonAnswer3.setVisibility(View.GONE);
            buttonAnswer4.setVisibility(View.GONE);
        }
    }

    public void answerOnClick(View view) {
        Button b = (Button) view;
        gs.answer(String.valueOf(b.getText()));
        // TODO[3]: navigate to thresholds view
    }
}
