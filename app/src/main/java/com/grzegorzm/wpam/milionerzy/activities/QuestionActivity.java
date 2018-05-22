package com.grzegorzm.wpam.milionerzy.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.grzegorzm.wpam.milionerzy.R;
import com.grzegorzm.wpam.milionerzy.logic.GameSingleton;
import com.grzegorzm.wpam.milionerzy.logic.QuestionAsked;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuestionActivity extends AppCompatActivity {
    private GameSingleton gs = GameSingleton.getInstance();
    private static boolean questionLoaded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        showQuestion();
        TextView fifty = findViewById(R.id.textViewFiftyFifty);
        if (gs.isFiftyFiftyUnused())
            fifty.setBackgroundColor(Color.argb(0, 255, 255, 255));
        else
            fifty.setBackgroundColor(Color.argb(255, 200, 200, 200));
        TextView tel = findViewById(R.id.textViewTelephone);
        if (gs.isTelephoneCallUnused())
            tel.setBackgroundColor(Color.argb(0, 255, 255, 255));
        else
            tel.setBackgroundColor(Color.argb(255, 200, 200, 200));
        TextView aud = findViewById(R.id.textViewAudience);
        if (gs.isAudienceUnused())
            aud.setBackgroundColor(Color.argb(0, 255, 255, 255));
        else
            aud.setBackgroundColor(Color.argb(255, 200, 200, 200));
    }

    public void answerOnClick(View view) {
        TextView b = (TextView) view;
        String s = String.valueOf(b.getText());
        Pattern pattern = Pattern.compile("(.*)\\t+\\d+%");
        Matcher matcher = pattern.matcher(s);
        if (matcher.find()) {
            s = matcher.group(1);
        }
        gs.answer(s);
        if (gs.getLastQuestion() == null)
            showFinalScore();
        else
            showThresholds();
    }

    public void okOnClick(View view) {
        finish();
    }

    public void thresholdOnClick(View view) {
        showQuestion();
    }

    public void fiftyOnClick(View view) {
        if (gs.isFiftyFiftyUnused()) {
            gs.fiftyFifty();
            TextView fifty = findViewById(R.id.textViewFiftyFifty);
            fifty.setBackgroundColor(Color.argb(255, 200, 200, 200));
            showQuestion();
        }
    }

    public void audienceOnClick(View view) {
        if (gs.isAudienceUnused()) {
            gs.audience();
            TextView aud = findViewById(R.id.textViewAudience);
            aud.setBackgroundColor(Color.argb(255, 200, 200, 200));
            showQuestion();
        }
    }

    public void telephoneOnClick(View view) {
        if (gs.isTelephoneCallUnused()) {
            gs.telephoneCall();
            TextView tel = findViewById(R.id.textViewTelephone);
            tel.setBackgroundColor(Color.argb(255, 200, 200, 200));
            showQuestion();
        }
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

        TextView textView = findViewById(R.id.textViewQuestion);
        TextView buttonAnswer1 = findViewById(R.id.buttonAnswer1);
        TextView buttonAnswer2 = findViewById(R.id.buttonAnswer2);
        TextView buttonAnswer3 = findViewById(R.id.buttonAnswer3);
        TextView buttonAnswer4 = findViewById(R.id.buttonAnswer4);
        textView.setText(lastQuestion.getQuestionText());

        TextView textViewThreshold = findViewById(R.id.textViewThreshold);
        textViewThreshold.setText("Pytanie za: " + lastQuestion.getThreshold().toString());

        List<Integer> audiencePercentage = lastQuestion.getAudiencePercentage();
        if (audiencePercentage != null && (audiencePercentage.size() == answers.size())) {
            buttonAnswer1.setText(answers.get(0) + "\t" + audiencePercentage.get(0) + "%");
            buttonAnswer2.setText(answers.get(1) + "\t" + audiencePercentage.get(1) + "%");
            buttonAnswer1.setVisibility(View.VISIBLE);
            buttonAnswer2.setVisibility(View.VISIBLE);
            if (answers.size() > 2) {
                buttonAnswer3.setText(answers.get(2) + "\t" + audiencePercentage.get(2) + "%");
                buttonAnswer4.setText(answers.get(3) + "\t" + audiencePercentage.get(3) + "%");
                buttonAnswer3.setVisibility(View.VISIBLE);
                buttonAnswer4.setVisibility(View.VISIBLE);
            } else {
                buttonAnswer3.setVisibility(View.GONE);
                buttonAnswer4.setVisibility(View.GONE);
            }
        } else {
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
        questionLoaded = true;
        String telephoneMessage = lastQuestion.getTelephoneMessage();
        TextView tm = findViewById(R.id.textViewTelephoneMessage);
        if (telephoneMessage != null) {
            tm.setText(telephoneMessage);
            tm.setVisibility(View.VISIBLE);
        } else {
            tm.setVisibility(View.GONE);
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
                tv.setBackgroundColor(Color.rgb(0, 170, 0));
            else if (i == lastLevel)
                tv.setBackgroundColor(Color.rgb(204, 204, 0));
            if (i == GameSingleton.guaranty[0] || i == GameSingleton.guaranty[1])
                tv.setTextColor(Color.rgb(0, 150, 150));
        }
        questionLoaded = false;
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if (!questionLoaded)
                    showQuestion();
            }
        }, 3000);
    }

    private void showFinalScore() {
        View viewQuestion = findViewById(R.id.scrollViewQuestion);
        View viewThreshold = findViewById(R.id.scrollViewThreshold);
        View viewScore = findViewById(R.id.scrollViewScore);
        viewThreshold.setVisibility(View.GONE);
        viewQuestion.setVisibility(View.GONE);
        viewScore.setVisibility(View.VISIBLE);

        TextView textViewEndGame = findViewById(R.id.textViewEndGame);
        textViewEndGame.setText(gs.getEndGameMessage());
        TextView textView = findViewById(R.id.textViewScore);
        textView.setText("Final Score: " + gs.getTotalPoints());
    }
}
