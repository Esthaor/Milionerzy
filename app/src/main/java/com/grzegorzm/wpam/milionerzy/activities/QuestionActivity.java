package com.grzegorzm.wpam.milionerzy.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
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
        showQuestion();
    }

    public void answerOnClick(View view) {
        Button b = (Button) view;
        gs.answer(String.valueOf(b.getText()));
        if (gs.getLastQuestion() == null)
            showFinalScore();
        else
            showThresholds();
    }

    public void okOnClick(View view) {
        finish();
    }

    private void showQuestion() {
        View viewQuestion = findViewById(R.id.scrollViewQuestion);
        View viewThreshold = findViewById(R.id.scrollViewThreshold);
        View viewScore = findViewById(R.id.scrollViewScore);
        viewThreshold.setVisibility(View.GONE);
        viewQuestion.setVisibility(View.VISIBLE);
        viewScore.setVisibility(View.GONE);

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

    private void showThresholds() {
        View viewQuestion = findViewById(R.id.scrollViewQuestion);
        View viewThreshold = findViewById(R.id.scrollViewThreshold);
        View viewScore = findViewById(R.id.scrollViewScore);
        viewThreshold.setVisibility(View.VISIBLE);
        viewQuestion.setVisibility(View.GONE);
        viewScore.setVisibility(View.GONE);

        int lastLevel = gs.getLastLevel();
        TextView[] views = {
                findViewById(R.id.textViewLevel01),
                findViewById(R.id.textViewLevel02),
                findViewById(R.id.textViewLevel03),
                findViewById(R.id.textViewLevel04),
                findViewById(R.id.textViewLevel05),
                findViewById(R.id.textViewLevel06),
                findViewById(R.id.textViewLevel07),
                findViewById(R.id.textViewLevel08),
                findViewById(R.id.textViewLevel09),
                findViewById(R.id.textViewLevel10),
                findViewById(R.id.textViewLevel11),
                findViewById(R.id.textViewLevel12)
        };
        for (int i = 0; i < 12; i++) {
            TextView tv = views[i];
            if (i < lastLevel)
                tv.setBackgroundColor(Color.parseColor("#008800"));
            else if (i == lastLevel)
                tv.setBackgroundColor(Color.parseColor("#008888"));
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                showQuestion();
            }
        }, 2000);
    }

    private void showFinalScore() {
        View viewQuestion = findViewById(R.id.scrollViewQuestion);
        View viewThreshold = findViewById(R.id.scrollViewThreshold);
        View viewScore = findViewById(R.id.scrollViewScore);
        viewThreshold.setVisibility(View.GONE);
        viewQuestion.setVisibility(View.GONE);
        viewScore.setVisibility(View.VISIBLE);

        TextView textView = findViewById(R.id.textViewScore);
        textView.setText("Final Score: " + gs.getTotalPoints());
    }
}
