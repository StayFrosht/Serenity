package com.example.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class Exercise extends AppCompatActivity {
    private String color;
    private String emote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_exercise);
        Intent intent = getIntent();
        color = intent.getStringExtra("color");
        emote = intent.getStringExtra("emote");
        Log.d("Quote", "Color: " + color + ", Emote: " + emote);

        ImageView btnMenu = findViewById(R.id.btnMenu3);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Exercise.this, com.example.application.MainAct.class);
                startActivity(intent);
            }
        });
        ImageView backButton = findViewById(R.id.btnBack3);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ImageView btnSettings = findViewById(R.id.btnSetting3);
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Exercise.this, com.example.application.Settings.class);
                startActivity(intent);
            }
        });

        ImageView btnNext = findViewById(R.id.btnNext3);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Exercise.this, Surround.class);
                intent.putExtra("color", color);
                intent.putExtra("emote", emote);
                startActivity(intent);
            }
        });
    }
}