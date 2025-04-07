package com.example.theicecreamy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ManageFoodItemsActivity extends AppCompatActivity {

    private RecyclerView recyclerViewFoodItems;
    private FloatingActionButton btnAddFoodItem;
    private DatabaseReference databaseFoodItems;
    private List<FoodItem> foodItemList;
    private FoodItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_food_items);

        // Initialize Firebase database reference
        databaseFoodItems = FirebaseDatabase.getInstance().getReference("food_items");

        // Initialize views
        recyclerViewFoodItems = findViewById(R.id.recyclerViewFoodItems);
        btnAddFoodItem = findViewById(R.id.btnAddFoodItem);

        // Initialize food item list and adapter
        foodItemList = new ArrayList<>();
        adapter = new FoodItemAdapter(foodItemList, this);

        // Set up RecyclerView
        recyclerViewFoodItems.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewFoodItems.setAdapter(adapter);

        // Load food items from Firebase
        loadFoodItems();

        // Add Food Item button click listener
        btnAddFoodItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageFoodItemsActivity.this, add_restaurant.class);
                startActivity(intent);
            }
        });
    }

    private void loadFoodItems() {
        databaseFoodItems.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                foodItemList.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    FoodItem foodItem = postSnapshot.getValue(FoodItem.class);
                    foodItemList.add(foodItem);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ManageFoodItemsActivity.this, "Failed to load food items", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
