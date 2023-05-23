package com.example.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainAct extends AppCompatActivity {
    private Intent backgroundMusicIntent;
    private SharedPreferences sharedPreferences;
    private boolean isBackgroundMusicEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
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

        ImageView btnSettings = findViewById(R.id.btnsettings);
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainAct.this, Settings.class));
            }
        });

        // Initialize SharedPreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        // Retrieve the background music setting from SharedPreferences
        isBackgroundMusicEnabled = sharedPreferences.getBoolean(Settings.PREF_KEY_BACKGROUND_MUSIC, true);

        // Start or stop the background music service based on the retrieved setting
        if (isBackgroundMusicEnabled) {
            startBackgroundMusic();
        } else {
            stopBackgroundMusic();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Stop the BackgroundMusicService
        stopService(backgroundMusicIntent);
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

    @Override
    protected void onResume() {
        super.onResume();

        // Retrieve the updated background music setting from SharedPreferences
        boolean updatedBackgroundMusicSetting = sharedPreferences.getBoolean(Settings.PREF_KEY_BACKGROUND_MUSIC, true);

        // Check if the background music setting has changed
        if (isBackgroundMusicEnabled != updatedBackgroundMusicSetting) {
            isBackgroundMusicEnabled = updatedBackgroundMusicSetting;

            // Start or stop the background music service based on the updated setting
            if (isBackgroundMusicEnabled) {
                startBackgroundMusic();
            } else {
                stopBackgroundMusic();
            }
        }
    }
}


