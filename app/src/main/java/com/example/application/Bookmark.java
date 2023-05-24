package com.example.application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

public class Bookmark extends AppCompatActivity {

    private FlowLayout flowLayout;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_bookmark);

        flowLayout = findViewById(R.id.flowLayout);

        loadSavedQuotes();

        ImageView btnMenu = findViewById(R.id.btnMenu6);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Bookmark.this, com.example.application.MainAct.class);
                startActivity(intent);
            }
        });
        ImageView btnSettings = findViewById(R.id.btnSetting6);
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Bookmark.this, com.example.application.Settings.class);
                startActivity(intent);
            }
        });
        ImageView backButton = findViewById(R.id.btnback2);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    void addQuoteToFlowLayout(String quote) {
        CardView cardView = new CardView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10, 10, 10, 10);
        cardView.setLayoutParams(layoutParams);

        cardView.setRadius(8);

        LinearLayout quoteLayout = new LinearLayout(this);
        quoteLayout.setOrientation(LinearLayout.VERTICAL);
        cardView.addView(quoteLayout);

        TextView textView = new TextView(this);
        textView.setText(quote);
        textView.setTextSize(18);
        textView.setPadding(16, 16, 16, 16);
        textView.setGravity(Gravity.CENTER); // Center the text within the TextView
        quoteLayout.addView(textView);

        Button deleteButton = new Button(this);
        deleteButton.setText("Delete");
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle delete button click here
                removeQuoteFromFlowLayout(cardView, quote);
            }
        });

        quoteLayout.addView(deleteButton);

        flowLayout.addView(cardView);
    }

    void removeQuoteFromFlowLayout(CardView cardView, String quote) {
        flowLayout.removeView(cardView);
        removeFromSharedPreferences(quote);
    }

    void removeFromSharedPreferences(String quote) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyFavorites", MODE_PRIVATE);
        int savedQuotesCount = sharedPreferences.getInt("savedQuotesCount", 0);

        for (int i = 1; i <= savedQuotesCount; i++) {
            String quoteKey = "SaveQuote" + i;
            String savedQuote = sharedPreferences.getString(quoteKey, "");

            if (savedQuote.equals(quote)) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(quoteKey);
                editor.apply();
                break;
            }
        }
    }



    private void loadSavedQuotes() {
        sharedPreferences = getSharedPreferences("MyFavorites", MODE_PRIVATE);
        int savedQuotesCount = sharedPreferences.getInt("savedQuotesCount", 0);
        Set<String> uniqueQuotes = new HashSet<>();

        for (int i = 1; i <= savedQuotesCount; i++) {
            String quoteKey = "SaveQuote" + i;
            String quote = sharedPreferences.getString(quoteKey, "");
            if (!quote.isEmpty() && !uniqueQuotes.contains(quote)) {
                addQuoteToFlowLayout(quote);
                uniqueQuotes.add(quote);
            }
        }
    }

}
