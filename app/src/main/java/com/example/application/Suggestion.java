package com.example.application;

import androidx.appcompat.app.AppCompatActivity;
import com.example.application.R;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class Suggestion extends AppCompatActivity {

    private String color;
    private String emote;
    private CheckBox checkBox;
    private SharedPreferences sharedPreferences;
    private boolean isChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_suggestion);

        sharedPreferences = getSharedPreferences("MyFavorites", MODE_PRIVATE);

        Intent intent = getIntent();
        if (intent != null) {
            color = intent.getStringExtra("color");
            emote = intent.getStringExtra("emote");
            Log.d("Quote", "Color: " + color + ", Emote: " + emote);


            String suggestion = getSuggestionFromEmote(emote);

            TextView txtSuggestion = findViewById(R.id.txtSuggestion);
            txtSuggestion.setText(suggestion);

            TextView txtTask = findViewById(R.id.txtTask);
            String task = getTaskFromEmote(emote);
            txtTask.setText(task);
        }

        checkBox = findViewById(R.id.checkBox);
        boolean isFavorite = sharedPreferences.getBoolean(emote, false);
        checkBox.setChecked(isFavorite);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = checkBox.isChecked();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(emote, isChecked);
                editor.apply();
            }
        });

        ImageView backButton = findViewById(R.id.btnBack2);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageView btnMenu = findViewById(R.id.btnMenu2);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Suggestion.this, com.example.application.MainAct.class);
                startActivity(intent);
            }
        });

        ImageView btnSettings = findViewById(R.id.btnSetting2);
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Suggestion.this, com.example.application.Settings.class);
                startActivity(intent);
            }
        });

        ImageView btnNext = findViewById(R.id.btnNext2);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Suggestion.this, Exercise.class);
                intent.putExtra("color", color);
                intent.putExtra("emote", emote);
                startActivity(intent);
            }
        });

        CheckBox checkBox = findViewById(R.id.checkBox);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isChecked = !isChecked;
                updateCheckboxAppearance();
            }
        });

    }

    private String getSuggestionFromEmote(String emote) {
        if (emote != null) {
            int suggestionResId = 0;

            if (emote.equals("Calm")){
                suggestionResId = R.string.calm_suggestion;
            } else if (emote.equals("Anxious")) {
                suggestionResId = R.string.anxious_suggestion;
            } else if (emote.equals("Sad")) {
                suggestionResId = R.string.sad_suggestion;
            } else if (emote.equals("Excited")) {
                suggestionResId = R.string.excited_suggestion;
            } else if (emote.equals("Disgust")) {
                suggestionResId = R.string.disgust_suggestion;
            } else if (emote.equals("Happy")) {
                suggestionResId = R.string.happy_suggestion;
            } else if (emote.equals("In Love")) {
                suggestionResId = R.string.in_love_suggestion;
            } else if (emote.equals("Angry")) {
                suggestionResId = R.string.angry_suggestion;
            } else if (emote.equals("Shy")) {
                suggestionResId = R.string.shy_suggestion;
            } else if (emote.equals("Disappointed")) {
                suggestionResId = R.string.disappointed_suggestion;
            }

            if (suggestionResId != 0) {
                return getString(suggestionResId);
            }
        }

        return "";
    }
    private void updateCheckboxAppearance() {
        CheckBox checkBox = findViewById(R.id.checkBox);
        if (isChecked) {
            checkBox.setChecked(true);
            checkBox.setButtonTintList( ColorStateList.valueOf(getResources().getColor(R.color.checkedColor)));
        } else {
            checkBox.setChecked(false);
            checkBox.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.uncheckedColor)));
        }
    }


    private String getTaskFromEmote(String emote) {

        String task = "";

        if (emote.equals("Calm")) {
            task = getString(R.string.calm_task);
        } else if (emote.equals("Anxious")) {
            task = getString(R.string.anxious_task);
        } else if (emote.equals("Sad")) {
            task = getString(R.string.sad_task);
        } else if (emote.equals("Excited")) {
            task = getString(R.string.excited_task);
        } else if (emote.equals("Disgust")) {
            task = getString(R.string.disgust_task);
        } else if (emote.equals("Happy")) {
            task = getString(R.string.happy_task);
        } else if (emote.equals("In Love")) {
            task = getString(R.string.in_love_task);
        } else if (emote.equals("Angry")) {
            task = getString(R.string.angry_task);
        } else if (emote.equals("Shy")) {
            task = getString(R.string.shy_task);
        } else if (emote.equals("Disappointed")) {
            task = getString(R.string.disappointed_task);
        }

        return task;
    }
}
