package com.example.application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Feel extends AppCompatActivity implements View.OnClickListener {
    private Intent backgroundMusicIntent;
    private boolean isFinishing = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feel);

        // Set click listeners for buttons
        findViewById(R.id.btnCalm).setOnClickListener(this);
        findViewById(R.id.btnAnxious).setOnClickListener(this);
        findViewById(R.id.btnSad).setOnClickListener(this);
        findViewById(R.id.btnExcited).setOnClickListener(this);
        findViewById(R.id.btnDisgust).setOnClickListener(this);
        findViewById(R.id.btnHappy).setOnClickListener(this);
        findViewById(R.id.btnInlove).setOnClickListener(this);
        findViewById(R.id.btnAngry).setOnClickListener(this);
        findViewById(R.id.btnShy).setOnClickListener(this);
        findViewById(R.id.btnDissapointed).setOnClickListener(this);

        ImageView backButton = findViewById(R.id.back3);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFinishing = true; // Set the flag to indicate activity is being finished
                finish(); // Close the current activity and return to the previous page
            }
        });

        backgroundMusicIntent = new Intent(this, BackgroundMusicService.class);
        startService(backgroundMusicIntent);
    }

    @Override
    public void onClick(View v) {
        String selectedValue = "";

        // Determine the selected button and set the corresponding value
        switch (v.getId()) {
            case R.id.btnCalm:
                selectedValue = "Calm";
                break;
            case R.id.btnAnxious:
                selectedValue = "Anxious";
                break;
            case R.id.btnSad:
                selectedValue = "Sad";
                break;
            case R.id.btnExcited:
                selectedValue = "Excited";
                break;
            case R.id.btnDisgust:
                selectedValue = "Disgust";
                break;
            case R.id.btnHappy:
                selectedValue = "Happy";
                break;
            case R.id.btnInlove:
                selectedValue = "In Love";
                break;
            case R.id.btnAngry:
                selectedValue = "Angry";
                break;
            case R.id.btnShy:
                selectedValue = "Shy";
                break;
            case R.id.btnDissapointed:
                selectedValue = "Dissapointed";
                break;
        }

        // Start Color activity and pass the selected value
        Intent intent = new Intent(Feel.this, Color.class);
        intent.putExtra("selectedValue", selectedValue);
        startActivity(intent);
    }


}
