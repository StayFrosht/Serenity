package com.example.application;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class Settings extends AppCompatActivity {

    public static final String PREF_KEY_BACKGROUND_MUSIC = "background_music";

    private Switch bgMusicSwitch;
    private Intent backgroundMusicServiceIntent;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_settings);

        ImageView backButton = findViewById(R.id.btnback);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Close the current activity and return to the previous page
            }
        });

        // Initialize SharedPreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        bgMusicSwitch = findViewById(R.id.switch1);
        bgMusicSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Save the state in SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(PREF_KEY_BACKGROUND_MUSIC, isChecked);
                editor.apply();

                if (isChecked) {
                    // Turn on the music
                    startBackgroundMusic();
                } else {
                    // Turn off the music
                    stopBackgroundMusic();
                }
            }
        });

        // Retrieve the state from SharedPreferences
        boolean isBackgroundMusicEnabled = sharedPreferences.getBoolean(PREF_KEY_BACKGROUND_MUSIC, true);
        bgMusicSwitch.setChecked(isBackgroundMusicEnabled);

        if (isBackgroundMusicEnabled) {
            // Start the background music if it was previously enabled
            startBackgroundMusic();
        }
    }

    private void startBackgroundMusic() {
        if (backgroundMusicServiceIntent == null) {
            backgroundMusicServiceIntent = new Intent(this, BackgroundMusicService.class);
            startService(backgroundMusicServiceIntent);
        }
    }

    private void stopBackgroundMusic() {
        if (backgroundMusicServiceIntent != null) {
            stopService(backgroundMusicServiceIntent);
            backgroundMusicServiceIntent = null;
        }
    }
}
