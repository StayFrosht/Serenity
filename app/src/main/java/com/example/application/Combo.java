package com.example.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.Color;
public class Combo extends AppCompatActivity {
    private Intent backgroundMusicIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_combo);

        backgroundMusicIntent = new Intent(this, BackgroundMusicService.class);
        startService(backgroundMusicIntent);
        // Retrieve the selected color and emote from the intent
        String color = getIntent().getStringExtra("color");
        String emote = getIntent().getStringExtra("emote");

        // Set the background color based on the selected color
        setComboBackgroundColor(color);

        // Update the emote image and text
        setEmote(emote);

        ImageView backButton = findViewById(R.id.back2);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Close the current activity and return to the previous page
            }
        });

    }

    private void setComboBackgroundColor(String color) {
        View bg2 = findViewById(R.id.bg2);
        View bg3 = findViewById(R.id.bg3);


        switch (color) {
            case "Red":
                bg2.setBackgroundColor(Color.parseColor("#FD8D8A"));
                bg3.setBackgroundColor(Color.parseColor("#FD8D8A"));
                break;
            case "Blue":
                bg2.setBackgroundColor(Color.parseColor("#C5D5F3"));
                bg3.setBackgroundColor(Color.parseColor("#C5D5F3"));
                break;
            case "Green":
                bg2.setBackgroundColor(Color.parseColor("#A7D8B4"));
                bg3.setBackgroundColor(Color.parseColor("#A7D8B4"));
                break;
            case "Pink":
                bg2.setBackgroundColor(Color.parseColor("#F09CA7"));
                bg3.setBackgroundColor(Color.parseColor("#F09CA7"));
                break;
            case "Purple":
                bg2.setBackgroundColor(Color.parseColor("#C5B9E8"));
                bg3.setBackgroundColor(Color.parseColor("#C5B9E8"));
                break;
            case "Yellow":
                bg2.setBackgroundColor(Color.parseColor("#F8E39C"));
                bg3.setBackgroundColor(Color.parseColor("#F8E39C"));
                break;
        }
    }

    private void setEmote(String emote) {
        ImageView emoteImageView = findViewById(R.id.emote);
        TextView emoteTextView = findViewById(R.id.emoteText);
        if (emote != null) {
            switch (emote) {
                case "Shy":
                    emoteImageView.setImageResource(R.drawable.sh);
                    emoteTextView.setText("Shy");
                    break;
                case "Happy":
                    emoteImageView.setImageResource(R.drawable.hap);
                    emoteTextView.setText("Happy");
                    break;
                case "Sad":
                    emoteImageView.setImageResource(R.drawable.sdy);
                    emoteTextView.setText("Sad");
                    break;
                case "Calm":
                    emoteImageView.setImageResource(R.drawable.kalm);
                    emoteTextView.setText("Calm");
                    break;
                case "Excited":
                    emoteImageView.setImageResource(R.drawable.excy);
                    emoteTextView.setText("Excited");
                    break;
                case "Disgust":
                    emoteImageView.setImageResource(R.drawable.disgy);
                    emoteTextView.setText("Disgust");
                    break;
                case "In Love":
                    emoteImageView.setImageResource(R.drawable.inly);
                    emoteTextView.setText("In Love");
                    break;
                case "Dissapointed":
                    emoteImageView.setImageResource(R.drawable.dissy);
                    emoteTextView.setText("Dissapointed");
                    break;
                case "Angry":
                    emoteImageView.setImageResource(R.drawable.angy);
                    emoteTextView.setText("Angry");
                    break;
                case "Anxious":
                    emoteImageView.setImageResource(R.drawable.anxy);
                    emoteTextView.setText("Anxious");
                    break;
                // Add cases for other emotes
            }
        }
    }

}
