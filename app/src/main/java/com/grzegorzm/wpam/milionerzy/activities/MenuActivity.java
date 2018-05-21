package com.grzegorzm.wpam.milionerzy.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.grzegorzm.wpam.milionerzy.R;
import com.grzegorzm.wpam.milionerzy.model.data.QuestionDbAdapter;
import com.grzegorzm.wpam.milionerzy.model.data.QuestionPopulator;
import com.grzegorzm.wpam.milionerzy.model.entity.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MenuActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.grzegorzm.milionerzy.MESSAGE";
    public static QuestionDbAdapter dbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbAdapter = new QuestionDbAdapter(getApplicationContext());
        dbAdapter.open();
        setContentView(R.layout.activity_menu);
    }

    public void newGameOnClick(View view) {
        Intent intent = new Intent(this, QuestionActivity.class);
        List<Question> questions = dbAdapter.getAllQuestionFromThreshold(500);
        Random r = new Random();
        int i = r.nextInt(questions.size());
        Question q = questions.get(i);
        intent.putExtra(EXTRA_MESSAGE, q.getQuestionText());
        startActivity(intent);
    }

    public void exitButtonOnClick(View view) {
        finish();
        System.exit(0);
    }

    @Override
    protected void onDestroy() {
        if (dbAdapter != null)
            dbAdapter.close();
        super.onDestroy();
    }
}
