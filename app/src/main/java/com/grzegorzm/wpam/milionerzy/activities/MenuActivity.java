package com.grzegorzm.wpam.milionerzy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.grzegorzm.wpam.milionerzy.R;
import com.grzegorzm.wpam.milionerzy.logic.GameSingleton;
import com.grzegorzm.wpam.milionerzy.model.data.QuestionDbAdapter;

public class MenuActivity extends AppCompatActivity {
    public static QuestionDbAdapter dbAdapter;

    private GameSingleton gs = GameSingleton.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbAdapter = new QuestionDbAdapter(getApplicationContext());
        dbAdapter.open();
        setContentView(R.layout.activity_menu);
        gs.loadGame();

        TextView resumeButton = findViewById(R.id.buttonResume);
        if (gs.getLastQuestion() != null) {
            resumeButton.setVisibility(View.VISIBLE);
        } else {
            resumeButton.setVisibility(View.GONE);
        }
    }

    public void newGameOnClick(View view) {
        Intent intent = new Intent(this, QuestionActivity.class);
        gs.startGame();
        startActivity(intent);
    }

    public void resumeOnClick(View view) {
        Intent intent = new Intent(this, QuestionActivity.class);
        startActivity(intent);
    }

    public void exitButtonOnClick(View view) {
        finish();
        System.exit(0);
    }

    public void highScoresOnClick(View view) {
        Intent intent = new Intent(this, HighScoresActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        gs.loadGame();
        TextView resumeButton = findViewById(R.id.buttonResume);
        if (gs.getLastQuestion() != null) {
            resumeButton.setVisibility(View.VISIBLE);
        } else {
            resumeButton.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        if (dbAdapter != null)
            dbAdapter.close();
        super.onDestroy();
    }
}
