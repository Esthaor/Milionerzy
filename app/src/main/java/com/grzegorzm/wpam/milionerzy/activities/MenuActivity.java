package com.grzegorzm.wpam.milionerzy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.grzegorzm.wpam.milionerzy.R;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void newGameOnClick(View view) {
        Intent intent = new Intent(this, QuestionActivity.class);
        String message = "treść";
        intent.putExtra("Test", message);
        startActivity(intent);
    }

    public void exitButtonOnClick(View view) {
        finish();
        System.exit(0);
    }
}
