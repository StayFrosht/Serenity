package com.example.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class Color extends AppCompatActivity {
    private Intent backgroundMusicIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_color);



        String selectedValue = getIntent().getStringExtra("selectedValue");


        ImageView redBox = findViewById(R.id.redBox);
        ImageView blueBox = findViewById(R.id.blueBox);
        ImageView greenBox = findViewById(R.id.greenBox);
        ImageView pinkBox = findViewById(R.id.pinkBox);
        ImageView purpleBox = findViewById(R.id.purpleBox);
        ImageView yellowBox = findViewById(R.id.yellowBox);

        ImageView redEmote = findViewById(R.id.redEmote);
        ImageView blueEmote = findViewById(R.id.blueEmote);
        ImageView greenEmote = findViewById(R.id.greenEmote);
        ImageView pinkEmote = findViewById(R.id.pinkEmote);
        ImageView purpleEmote = findViewById(R.id.purpleEmote);
        ImageView yellowEmote = findViewById(R.id.yellowEmote);


        redBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openComboActivity("Red",selectedValue);
            }
        });


        blueBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openComboActivity("Blue",selectedValue);
            }
        });

        greenBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openComboActivity("Green",selectedValue);
            }
        });

        pinkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openComboActivity("Pink",selectedValue);
            }
        });

        purpleBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openComboActivity("Purple",selectedValue);
            }
        });

        yellowBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openComboActivity("Yellow",selectedValue);
            }
        });
        ImageView backButton = findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (selectedValue != null) {
            switch (selectedValue) {
                case "Sad":
                    setEmoteImages(R.drawable.sdy);
                    break;
                case "Happy":
                    setEmoteImages(R.drawable.hap);
                    break;
                case "Angry":
                    setEmoteImages(R.drawable.angy);
                    break;
                case "Calm":
                    setEmoteImages(R.drawable.kalm);
                    break;
                case "Excited":
                    setEmoteImages(R.drawable.excy);
                    break;
                case "In Love":
                    setEmoteImages(R.drawable.inly);
                    break;
                case "Anxious":
                    setEmoteImages(R.drawable.anxy);
                    break;
                case "Disgust":
                    setEmoteImages(R.drawable.disgy);
                    break;
                case "Shy":
                    setEmoteImages(R.drawable.sh);
                    break;
                case "Disappointed":
                    setEmoteImages(R.drawable.dissy);
                    break;
            }
        }
    }

    private void openComboActivity(String color, String emote) {
        Intent intent = new Intent(Color.this, Combo.class);
        intent.putExtra("color", color);
        intent.putExtra("emote", emote);

        startActivity(intent);
    }


    private void setEmoteImages(int resourceId) {
        ImageView redEmote = findViewById(R.id.redEmote);
        ImageView blueEmote = findViewById(R.id.blueEmote);
        ImageView greenEmote = findViewById(R.id.greenEmote);
        ImageView pinkEmote = findViewById(R.id.pinkEmote);
        ImageView purpleEmote = findViewById(R.id.purpleEmote);
        ImageView yellowEmote = findViewById(R.id.yellowEmote);

        redEmote.setImageResource(resourceId);
        blueEmote.setImageResource(resourceId);
        greenEmote.setImageResource(resourceId);
        pinkEmote.setImageResource(resourceId);
        purpleEmote.setImageResource(resourceId);
        yellowEmote.setImageResource(resourceId);
    }
}
