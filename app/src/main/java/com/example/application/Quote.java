package com.example.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.ColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import android.graphics.Color;

public class Quote extends AppCompatActivity {

    private String[] quotes;
    private int savedQuotesCount;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_quote);

        String color = getIntent().getStringExtra("color");
        String emote = getIntent().getStringExtra("emote");

        quotes = getResources().getStringArray(R.array.quotes);

        TextView randQuoteTextView = findViewById(R.id.randQuote);

        String randomQuote = getRandomQuote();
        randQuoteTextView.setText(randomQuote);


        ImageView btnCopy = findViewById(R.id.btnCopy);
        btnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Copy the quote to the clipboard
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("Quote", randomQuote);
                clipboardManager.setPrimaryClip(clipData);

                // Show a toast message indicating that the quote is copied
                Toast.makeText(Quote.this, "Copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });
        ImageButton btnHeart = findViewById(R.id.btnHeart);
        btnHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorFilter currentFilter = btnHeart.getColorFilter();
                int redColor = Color.parseColor("#FF0000");

                if (currentFilter == null) {
                    btnHeart.setColorFilter(redColor);
                    saveQuote(randomQuote);
                } else {
                    btnHeart.clearColorFilter();
                }

            }
        });



        ImageView btnNext = findViewById(R.id.btnNext5);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Quote.this, Suggestion.class);
                intent.putExtra("color", color);
                intent.putExtra("emote", emote);
                startActivity(intent);
            }
        });


        ImageView btnMenu = findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Quote.this, com.example.application.MainAct.class);
                startActivity(intent);
            }
        });
        ImageView btnSettings = findViewById(R.id.btnSetting);
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Quote.this, com.example.application.Settings.class);
                startActivity(intent);
            }
        });
        ImageView backButton = findViewById(R.id.btnBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private String getRandomQuote() {
        Random random = new Random();
        int index = random.nextInt(quotes.length);
        return quotes[index];
    }
    private void saveQuote(String quote) {
        sharedPreferences = getSharedPreferences("MyFavorites", MODE_PRIVATE);
        savedQuotesCount = sharedPreferences.getInt("savedQuotesCount", 0);
        savedQuotesCount++;
        String quoteKey = "SaveQuote" + savedQuotesCount;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(quoteKey, quote);
        editor.putInt("savedQuotesCount", savedQuotesCount);
        editor.apply();
    }
}
