package com.example.findsamepicturegame;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;


public class gamemanual extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamemanual);
        ActionBar bar = getSupportActionBar();
        bar.setTitle("같은그림찾기");


    }
}
