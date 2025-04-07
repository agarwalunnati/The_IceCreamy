package com.example.theicecreamy;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ImageView profileImage;
    private TextView usernameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        // Sample data
        List<String> iceCreamFlavors = new ArrayList<>();
        iceCreamFlavors.add("Chocolate");
        iceCreamFlavors.add("Vanilla");
        iceCreamFlavors.add("Strawberry");
        iceCreamFlavors.add("Choco Brownie");
        iceCreamFlavors.add("American Nuts");
        iceCreamFlavors.add("Kesar Pista");
        iceCreamFlavors.add("ButterScotch");
        iceCreamFlavors.add("Cookie And Cream");
        iceCreamFlavors.add("Black Current");

        // Set up RecyclerView
        IceCreamAdapter adapter = new IceCreamAdapter(iceCreamFlavors);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        usernameTextView = findViewById(R.id.usernameTextView);

        // Declare and initialize the username variable
        String username = "Guest"; // Default username or you can retrieve it from SharedPreferences

        // Get the greeting based on the current time
        String greeting = getGreeting();

        // Set the greeting and username in the TextView
        usernameTextView.setText(greeting + ", " + username + "!");

        profileImage = findViewById(R.id.userPhotoImageView);

        // Set click listener for the profile image
        profileImage.setOnClickListener(v -> {
            // Navigate to the User Profile Activity
            Intent intent = new Intent(MainActivity.this, profile.class);
            startActivity(intent);
        });

        ImageView havmor= findViewById(R.id.imageView2);
        havmor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to navigate to the CartActivity
                Intent intent = new Intent(MainActivity.this, Havnor.class);
                startActivity(intent);
            }
        });

        ImageView vadilal= findViewById(R.id.imageView6);
        vadilal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to navigate to the CartActivity
                Intent intent = new Intent(MainActivity.this, vadilal.class);
                startActivity(intent);
            }
        });

        ImageView cartImage = findViewById(R.id.shopping);

        // Set click listener on the cart image
        cartImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to navigate to the CartActivity
                Intent intent = new Intent(MainActivity.this, cart.class);
                startActivity(intent);
            }
        });

        ImageView home = findViewById(R.id.home);

        // Set click listener on the cart image
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to navigate to the CartActivity
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });



        ImageView search = findViewById(R.id.search);

        // Set click listener on the cart image
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to navigate to the CartActivity
                Intent intent = new Intent(MainActivity.this, search.class);
                startActivity(intent);
            }
        });
    }

    // Method to get the greeting based on the time of day
    private String getGreeting() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        if (hour >= 5 && hour < 12) {
            return "Good Morning";
        } else if (hour >= 12 && hour < 17) {
            return "Good Afternoon";
        } else if (hour >= 17 && hour < 21) {
            return "Good Evening";
        } else {
            return "Good Night";
        }
    }

    // Adapter Class
    class IceCreamAdapter extends RecyclerView.Adapter<IceCreamAdapter.IceCreamViewHolder> {

        private final List<String> iceCreamFlavors;

        public IceCreamAdapter(List<String> iceCreamFlavors) {
            this.iceCreamFlavors = iceCreamFlavors;
        }

        @NonNull
        @Override
        public IceCreamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ice_cream, parent, false);
            return new IceCreamViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull IceCreamViewHolder holder, int position) {
            String flavor = iceCreamFlavors.get(position);
            holder.flavorTextView.setText(flavor);
        }

        @Override
        public int getItemCount() {
            return iceCreamFlavors.size();
        }

        class IceCreamViewHolder extends RecyclerView.ViewHolder {
            TextView flavorTextView;

            public IceCreamViewHolder(@NonNull View itemView) {
                super(itemView);
                flavorTextView = itemView.findViewById(R.id.textFlavor);
            }
        }
    }
}
