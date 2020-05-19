package com.example.findsamepicturegame;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnStart,btnExplain,btnExit;
    SoundPool sound; //효과음
    int soundId1; //효과음ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar bar = getSupportActionBar();
        bar.setTitle("같은그림찾기");
        sound = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        sound.play(soundId1, 1f, 1f, 0, 0, 1f);

        btnStart=(Button)findViewById(R.id.btnStart);
        btnExplain=(Button)findViewById(R.id.btnExplain);
        btnExit=(Button)findViewById(R.id.btnExit);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, gamestart.class);
                startActivity(intent);
            }
        });

        btnExplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, gamemanual.class);
                startActivity(intent);
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
