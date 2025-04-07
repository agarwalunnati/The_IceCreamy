package com.example.theicecreamy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Delay of 3 seconds before moving to MainActivity
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(splash.this, first_page.class);
            startActivity(intent);
            finish(); // Close the SplashActivity
        }, 1500);
    }
}