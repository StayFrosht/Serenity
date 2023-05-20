package com.example.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainAct extends AppCompatActivity {
    private Intent backgroundMusicIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_mainact);



        // Set click listener for the "Start" ImageView
        ImageView btnStart = findViewById(R.id.btnstart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainAct.this, Feel.class));
            }
        });
        backgroundMusicIntent = new Intent(this, BackgroundMusicService.class);
        startService(backgroundMusicIntent);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Stop the BackgroundMusicService
        stopService(backgroundMusicIntent);
    }
}


