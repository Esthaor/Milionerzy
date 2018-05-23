package com.grzegorzm.wpam.milionerzy.activities;

import android.content.Context;
import android.graphics.drawable.Drawable;
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
    private boolean clickable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        showQuestion();
        TextView fifty = findViewById(R.id.textViewFiftyFifty);
        Context context = getApplicationContext();
        if (gs.isFiftyFiftyUnused())
            fifty.setBackground(context.getDrawable(R.drawable.ic_fiftyfifty));
        else
            fifty.setBackground(context.getDrawable(R.drawable.ic_fiftyfifty_used));
        TextView tel = findViewById(R.id.textViewTelephone);
        if (gs.isTelephoneCallUnused())
            tel.setBackground(context.getDrawable(R.drawable.ic_telephone));
        else
            tel.setBackground(context.getDrawable(R.drawable.ic_telephone_used));
        TextView aud = findViewById(R.id.textViewAudience);
        if (gs.isAudienceUnused())
            aud.setBackground(context.getDrawable(R.drawable.ic_audience));
        else
            aud.setBackground(context.getDrawable(R.drawable.ic_audience_used));
        clickable = true;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        gs.saveGame();
    }

    public void answerOnClick(final View view) {
        if (!clickable)
            return;
        clickable = false;
        TextView b = (TextView) view;
        String s = String.valueOf(b.getText());
        Pattern pattern = Pattern.compile("(.*)\\t+\\d+%");
        Matcher matcher = pattern.matcher(s);
        if (matcher.find()) {
            s = matcher.group(1);
        }
        final Drawable btnSel = getApplicationContext().getDrawable(R.drawable.answer_selected);
        final Drawable btnCorr = getApplicationContext().getDrawable(R.drawable.answer_correct);
        view.setBackground(btnSel);
        int index = gs.getCorrectAnswerIndex();
        final TextView correctAnswer;
        switch (index) {
            case 0:
                correctAnswer = findViewById(R.id.buttonAnswer1);
                break;
            case 1:
                correctAnswer = findViewById(R.id.buttonAnswer2);
                break;
            case 2:
                correctAnswer = findViewById(R.id.buttonAnswer3);
                break;
            default:
                correctAnswer = findViewById(R.id.buttonAnswer4);
        }
        final boolean answer = gs.answer(s);
        Handler h1 = new Handler();
        h1.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (answer)
                    view.setBackground(btnCorr);
                else
                    correctAnswer.setBackground(btnCorr);
                Handler h2 = new Handler();
                h2.postDelayed(new Runnable() {
                    public void run() {
                        if (gs.getLastQuestion() == null)
                            showFinalScore();
                        else
                            showThresholds();
                        clickable = true;
                    }
                }, 3000);
            }
        }, 2000);
    }

    public void okOnClick(View view) {
        gs.saveGame();
        finish();
    }

    public void thresholdOnClick(View view) {
        if (!clickable)
            return;
        clickable = false;
        showQuestion();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                clickable = true;
            }
        }, 1000);
    }

    public void fiftyOnClick(View view) {
        if (!clickable)
            return;
        if (gs.isFiftyFiftyUnused()) {
            Context context = getApplicationContext();
            gs.fiftyFifty();
            TextView fifty = findViewById(R.id.textViewFiftyFifty);
            fifty.setBackground(context.getDrawable(R.drawable.ic_fiftyfifty_used));
            showQuestion();
        }
    }

    public void audienceOnClick(View view) {
        if (!clickable)
            return;
        if (gs.isAudienceUnused()) {
            Context context = getApplicationContext();
            gs.audience();
            TextView aud = findViewById(R.id.textViewAudience);
            aud.setBackground(context.getDrawable(R.drawable.ic_audience_used));
            showQuestion();
        }
    }

    public void telephoneOnClick(View view) {
        if (!clickable)
            return;
        if (gs.isTelephoneCallUnused()) {
            Context context = getApplicationContext();
            gs.telephoneCall();
            TextView tel = findViewById(R.id.textViewTelephone);
            tel.setBackground(context.getDrawable(R.drawable.ic_telephone_used));
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

        Drawable btn = getApplicationContext().getDrawable(R.drawable.answer);
        TextView textView = findViewById(R.id.textViewQuestion);
        TextView buttonAnswer1 = findViewById(R.id.buttonAnswer1);
        buttonAnswer1.setBackground(btn);
        TextView buttonAnswer2 = findViewById(R.id.buttonAnswer2);
        buttonAnswer2.setBackground(btn);
        TextView buttonAnswer3 = findViewById(R.id.buttonAnswer3);
        buttonAnswer3.setBackground(btn);
        TextView buttonAnswer4 = findViewById(R.id.buttonAnswer4);
        buttonAnswer4.setBackground(btn);
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
        final Drawable btnSel = getApplicationContext().getDrawable(R.drawable.answer_selected);
        final Drawable btnCorr = getApplicationContext().getDrawable(R.drawable.answer_correct);
        final Drawable btn = getApplicationContext().getDrawable(R.drawable.answer);
        for (int i = 0; i < 12; i++) {
            TextView tv = views[i];
            if (i < lastLevel)
                tv.setBackground(btnCorr);
            else if (i == lastLevel)
                tv.setBackground(btnSel);
            else
                tv.setBackground(btn);
        }
        questionLoaded = false;
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if (!questionLoaded)
                    showQuestion();
            }
        }, 5000);
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
