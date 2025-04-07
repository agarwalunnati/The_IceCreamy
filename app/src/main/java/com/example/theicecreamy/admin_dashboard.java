package com.example.theicecreamy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class admin_dashboard extends AppCompatActivity {

    private TextView tvWelcomeAdmin;
    private ImageView imgRestaurant, imgFood, imgOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        // Initialize views
        tvWelcomeAdmin = findViewById(R.id.tvWelcomeAdmin);
        imgRestaurant = findViewById(R.id.restaurant);
        imgFood = findViewById(R.id.food);
        imgOrders = findViewById(R.id.orders);

        // Retrieve admin name from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String adminName = sharedPreferences.getString("USERNAME", "Admin");
        tvWelcomeAdmin.setText("Welcome, " + adminName);

        // Set onClick listeners for ImageViews
        imgRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Add Restaurant Activity
                Intent intent = new Intent(admin_dashboard.this, add_restaurant.class);
                startActivity(intent);
            }
        });

        imgFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Manage Food Items Activity
                Intent intent = new Intent(admin_dashboard.this, ManageFoodItemsActivity.class);
                startActivity(intent);
            }
        });

        imgOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to View Orders Activity
                Intent intent = new Intent(admin_dashboard.this, ViewOrdersActivity.class);
                startActivity(intent);
            }
        });
    }
}
