package com.example.theicecreamy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class CheckoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        // Retrieve username from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("USERNAME", "Guest");

        TextView checkoutTextView = findViewById(R.id.checkoutTextView);

        if (username.equals("Guest")) {
            // Prompt user to log in
            checkoutTextView.setText("Please log in to proceed with checkout.");
            Intent loginIntent = new Intent(CheckoutActivity.this, login.class);
            startActivity(loginIntent);
        } else {
            // Proceed with checkout
            checkoutTextView.setText("Proceeding with checkout as " + username);
        }
    }
}