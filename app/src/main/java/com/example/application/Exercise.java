package com.example.application;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.application.R;

public class Exercise extends AppCompatActivity {
    private String color;
    private String emote;
    private TextView inhaleTextView;
    private TextView holdTextView;
    private TextView exhaleTextView;
    private TextView hold2TextView;
    private Handler handler;
    private Runnable colorRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_exercise);

        Intent intent = getIntent();
        color = intent.getStringExtra("color");
        emote = intent.getStringExtra("emote");

        inhaleTextView = findViewById(R.id.Inhale);
        holdTextView = findViewById(R.id.Hold);
        exhaleTextView = findViewById(R.id.Exhale);
        hold2TextView = findViewById(R.id.Hold2);

        ImageView btnMenu = findViewById(R.id.btnMenu3);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Exercise.this, com.example.application.MainAct.class);
                startActivity(intent);
            }
        });

        ImageView backButton = findViewById(R.id.btnBack3);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageView btnSettings = findViewById(R.id.btnSetting3);
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Exercise.this, com.example.application.Settings.class);
                startActivity(intent);
            }
        });

        ImageView btnNext = findViewById(R.id.btnNext3);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Exercise.this, Surround.class);
                intent.putExtra("color", color);
                intent.putExtra("emote", emote);
                startActivity(intent);
            }
        });
        handler = new Handler();

        colorRunnable = new Runnable() {
            @Override
            public void run() {
                // Animate the colors for the inhale phase (4 seconds)
                animateBackgroundColor(inhaleTextView, getResources().getColor(R.color.color1), getResources().getColor(R.color.color2), 4000, getResources().getColor(R.color.white));

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Animate the colors for the hold phase (4 seconds)
                        animateBackgroundColor(holdTextView, getResources().getColor(R.color.color2), getResources().getColor(R.color.color3), 4000, getResources().getColor(R.color.white));

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // Animate the colors for the exhale phase (6 seconds)
                                animateBackgroundColor(exhaleTextView, getResources().getColor(R.color.color3), getResources().getColor(R.color.color4), 6000, getResources().getColor(R.color.white));

                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        // Animate the colors for the second hold phase (4 seconds)
                                        animateBackgroundColor(hold2TextView, getResources().getColor(R.color.color4), getResources().getColor(R.color.color5), 4000, getResources().getColor(R.color.white));

                                        // Post the colorRunnable again for the next iteration
                                        handler.postDelayed(colorRunnable, 4000);
                                    }
                                }, 6000);
                            }
                        }, 4000);
                    }
                }, 4000);
            }
        };

// Start the color transition
        handler.post(colorRunnable);

    }

    private void animateBackgroundColor(final View view, int startColor, int endColor, long duration, final int originalColor) {
        ObjectAnimator animator = ObjectAnimator.ofArgb(view, "backgroundColor", startColor, endColor);
        animator.setEvaluator(new ArgbEvaluator());
        animator.setDuration(duration);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setBackgroundColor(originalColor);
            }
        });
        animator.start();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Remove the color transition callbacks to prevent memory leaks
        handler.removeCallbacks(colorRunnable);
    }
}
