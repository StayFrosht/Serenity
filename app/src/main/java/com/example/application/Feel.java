package com.example.application;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Feel extends AppCompatActivity implements View.OnClickListener {

    private Intent backgroundMusicIntent;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
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
                finish(); // Close the current activity and return to the previous page
            }
        });

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        // Initialize backgroundMusicIntent
        backgroundMusicIntent = new Intent(this, BackgroundMusicService.class);

        // Start or stop the background music service based on the preference value
        updateBackgroundMusicState();
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
                selectedValue = "Disappointed";
                break;
        }

        // Start Color activity and pass the selected value
        Intent intent = new Intent(Feel.this, Color.class);
        intent.putExtra("selectedValue", selectedValue);
        startActivity(intent);
    }
    private void updateBackgroundMusicState() {
        // Get the background music preference value from SharedPreferences
        boolean isBackgroundMusicEnabled = sharedPreferences.getBoolean("PREF_KEY_BACKGROUND_MUSIC", true);

        // Start or stop the background music service based on the preference value
        if (isBackgroundMusicEnabled) {
            startBackgroundMusic();
        } else {
            stopBackgroundMusic();
        }
    }

    private void startBackgroundMusic() {
        if (backgroundMusicIntent == null) {
            backgroundMusicIntent = new Intent(this, BackgroundMusicService.class);
            startService(backgroundMusicIntent);
        }
    }

    private void stopBackgroundMusic() {
        if (backgroundMusicIntent != null) {
            stopService(backgroundMusicIntent);
            backgroundMusicIntent = null;
        }
    }


}
