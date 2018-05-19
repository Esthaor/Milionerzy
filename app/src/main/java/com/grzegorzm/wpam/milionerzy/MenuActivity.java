package com.grzegorzm.wpam.milionerzy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void newGameOnClick(View view) {

    }

    public void exitButtonOnClick(View view) {
        finish();
        System.exit(0);
    }
}
