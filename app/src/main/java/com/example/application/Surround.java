package com.example.application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.application.R;

import java.util.Random;

public class Surround extends AppCompatActivity {
    private String color;
    private String emote;
    private int[] colorArray = {
            R.color.color1,
            R.color.color2,
            R.color.color3,
            R.color.color4,
            R.color.color5,
            R.color.color6,
            R.color.color7,
            R.color.color8,
            R.color.color9,
            R.color.color10
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_surround);
        Intent intent = getIntent();
        color = intent.getStringExtra("color");
        emote = intent.getStringExtra("emote");
        Log.d("Quote", "Color: " + color + ", Emote: " + emote);

        // Set the background color of the activity
        View rootView = findViewById(android.R.id.content);
        rootView.setBackgroundColor(Color.parseColor(color));

        ImageView color1 = findViewById(R.id.color1);
        ImageView color2 = findViewById(R.id.color2);

        // Set random colors from the colorArray to the image views
        Random random = new Random();
        int randomColorIndex1 = random.nextInt(colorArray.length);
        int randomColorIndex2 = random.nextInt(colorArray.length);
        int blendedColor1 = ContextCompat.getColor(this, colorArray[randomColorIndex1]);
        int blendedColor2 = ContextCompat.getColor(this, colorArray[randomColorIndex2]);

        // Set the blended colors to the image views
        color1.setColorFilter(blendedColor1);
        color2.setColorFilter(blendedColor2);

        ImageView btnMenu = findViewById(R.id.btnMenu4);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Surround.this, com.example.application.MainAct.class);
                startActivity(intent);
            }
        });
        ImageView backButton = findViewById(R.id.btnBack4);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ImageView btnSettings = findViewById(R.id.btnSetting4);
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Surround.this, com.example.application.Settings.class);
                startActivity(intent);
            }
        });

        ImageView btnNext = findViewById(R.id.btnNext4);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Surround.this, Bookmark.class);
                intent.putExtra("color", color);
                intent.putExtra("emote", emote);
                startActivity(intent);
            }
        });
    }
}
