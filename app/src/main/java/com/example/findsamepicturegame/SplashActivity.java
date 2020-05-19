package com.example.findsamepicturegame;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


public class SplashActivity extends AppCompatActivity {
    ImageView splashGif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        splashGif =(ImageView)findViewById(R.id.splashGif);
        Glide.with(this).load(R.raw.pengsu).into(splashGif);

        thread_sleep sleep= new thread_sleep(this);
        sleep.start();

    }
    class thread_sleep extends Thread{
        Activity thisAct;
        thread_sleep(Activity theAct){
            thisAct = theAct;
        }
        public void run(){
            try{
                sleep(3000);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            Intent intent = new Intent(thisAct,MainActivity.class);
            startActivity(intent);
            finish();
        }

    }
}
