package com.example.theicecreamy;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class add_restaurant extends AppCompatActivity {

    private EditText etRestaurantName, etAddress, etContact, etCuisine;
    private Button btnSaveRestaurant;
    private DatabaseReference databaseRestaurants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);

        // Initialize Firebase database reference
        databaseRestaurants = FirebaseDatabase.getInstance().getReference("restaurants");

        // Initialize views
        etRestaurantName = findViewById(R.id.etRestaurantName);
        etAddress = findViewById(R.id.etAddress);
        etContact = findViewById(R.id.etContact);
        etCuisine = findViewById(R.id.etCuisine);
        btnSaveRestaurant = findViewById(R.id.btnSaveRestaurant);

        // Set onClickListener for the Save button
        btnSaveRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRestaurant();
            }
        });
    }

    private void saveRestaurant() {
        String name = etRestaurantName.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String contact = etContact.getText().toString().trim();
        String cuisine = etCuisine.getText().toString().trim();

        // Validate inputs
        if (TextUtils.isEmpty(name)) {
            etRestaurantName.setError("Name is required");
            return;
        }
        if (TextUtils.isEmpty(address)) {
            etAddress.setError("Address is required");
            return;
        }
        if (TextUtils.isEmpty(contact)) {
            etContact.setError("Contact is required");
            return;
        }
        if (TextUtils.isEmpty(cuisine)) {
            etCuisine.setError("Cuisine type is required");
            return;
        }

        // Generate a unique ID for the restaurant
        String id = databaseRestaurants.push().getKey();

        // Create a Restaurant object
        Restaurant restaurant = new Restaurant(id, name, address, contact, cuisine);

        // Save to Firebase
        if (id != null) {
            databaseRestaurants.child(id).setValue(restaurant).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(add_restaurant.this, "Restaurant added successfully", Toast.LENGTH_SHORT).show();
                    // Clear input fields
                    etRestaurantName.setText("");
                    etAddress.setText("");
                    etContact.setText("");
                    etCuisine.setText("");
                } else {
                    Toast.makeText(add_restaurant.this, "Failed to add restaurant", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
