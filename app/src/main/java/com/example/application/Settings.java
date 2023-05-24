package com.example.application;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class Settings extends AppCompatActivity {

    public static final String PREF_KEY_BACKGROUND_MUSIC = "background_music";
    private static final String CHANNEL_ID = "notification_channel";
    private static final int NOTIFICATION_ID = 1;
    private static final int REQUEST_NOTIFICATION_PERMISSION = 1;
    private Switch notifMusicSwitch;
    private Switch bgMusicSwitch;
    private Intent backgroundMusicServiceIntent;
    private SharedPreferences sharedPreferences;
    private NotificationManagerCompat notificationManager;
    private Handler handler;
    private Runnable delayedNotificationRunnable;

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
        notifMusicSwitch = findViewById(R.id.switch2);
        bgMusicSwitch = findViewById(R.id.switch1);
        notifMusicSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = sharedPreferences.edit();

                if (isChecked) {
                    showDelayedNotification();
                } else {
                    showNotificationOff();
                }
            }
        });
        bgMusicSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(PREF_KEY_BACKGROUND_MUSIC, isChecked);
                editor.apply();

                if (isChecked) {
                    startBackgroundMusic();
                } else {
                    stopBackgroundMusic();
                }
            }
        });

        boolean isBackgroundMusicEnabled = sharedPreferences.getBoolean(PREF_KEY_BACKGROUND_MUSIC, true);
        bgMusicSwitch.setChecked(isBackgroundMusicEnabled);

        if (isBackgroundMusicEnabled) {
            startBackgroundMusic();
        }

        notificationManager = NotificationManagerCompat.from(this);
        handler = new Handler();
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

    private void showDelayedNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Channel Name", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.mainlogo)
                .setContentTitle("Make Sure to Check on How you Feel")
                .setContentText("Open The app and check your colors.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, REQUEST_NOTIFICATION_PERMISSION);
        } else {
            notificationManager.notify(NOTIFICATION_ID, builder.build());

            // Delayed notification
            handler.removeCallbacks(delayedNotificationRunnable);
            delayedNotificationRunnable = new Runnable() {
                @Override
                public void run() {
                    sendDelayedNotification();
                }
            };
            handler.postDelayed(delayedNotificationRunnable, 5000); // Delay in milliseconds (5 seconds)
        }
    }

    private void showNotificationOff() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.mainlogo)
                .setContentTitle("Notification Title")
                .setContentText("Notifications for the app are turned off.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, REQUEST_NOTIFICATION_PERMISSION);
        } else {
            notificationManager.notify(NOTIFICATION_ID, builder.build());
        }
    }

    private void sendDelayedNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.mainlogo)
                .setContentTitle("Delayed Notification")
                .setContentText("Hi, thank you for turning on notifications! ❤️")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    private void cancelNotification() {
        notificationManager.cancel(NOTIFICATION_ID);
        handler.removeCallbacks(delayedNotificationRunnable);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_NOTIFICATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showNotification();
            }
        }
    }

    private void showNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Channel Name", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.mainlogo)
                .setContentTitle("You Have turned on Notification for Serenity shades")
                .setContentText("mwa mwa mwa mwa mwa")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, REQUEST_NOTIFICATION_PERMISSION);
        } else {
            notificationManager.notify(NOTIFICATION_ID, builder.build());
        }
    }
}
