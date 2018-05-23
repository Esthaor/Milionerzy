package com.grzegorzm.wpam.milionerzy.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.grzegorzm.wpam.milionerzy.R;
import com.grzegorzm.wpam.milionerzy.logic.GameSingleton;
import com.grzegorzm.wpam.milionerzy.model.entity.Score;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import static android.view.View.*;

public class HighScoresActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);

        GameSingleton gs = GameSingleton.getInstance();
        List<Score> highScores = gs.getHighScores();
        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lparams.setMargins(25, 10, 25, 10);

        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        LinearLayout layout = findViewById(R.id.tableHighScores);
        final float scale = getBaseContext().getResources().getDisplayMetrics().density;
        for (int i = 0; i < highScores.size(); i++) {
            Score s = highScores.get(i);
            TextView tv = new TextView(this, null, R.style.Record);
            tv.setLayoutParams(lparams);
            tv.setTextAppearance(R.style.Threshold);
            tv.setGravity(Gravity.CENTER);
            tv.setHeight((int) (30 * scale + 0.5f));
            tv.setBackground(getApplicationContext().getDrawable(R.drawable.answer));

            tv.setText(i + 1 + ". " + s.getScore() + " (" + df.format(s.getDate()) + ")");
            layout.addView(tv);
        }
        TextView tv = new TextView(this);
        tv.setLayoutParams(lparams);
        tv.setTextAppearance(R.style.Button);
        tv.setGravity(Gravity.CENTER);
        tv.setHeight((int) (60 * scale + 0.5f));
        tv.setBackground(getApplicationContext().getDrawable(R.drawable.border));
        tv.setText("Cofnij");
        tv.setClickable(true);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        layout.addView(tv);
    }
}
