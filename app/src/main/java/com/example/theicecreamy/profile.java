package com.example.theicecreamy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class profile extends AppCompatActivity {

    private TextView userName, userEmail, defaultAddress, orderHistory;
    private ImageView profilePicture;
    private Button manageAddresses, managePaymentMethods, contactSupport, logoutButton, deactivateButton, loginButton;
    private CheckBox notificationToggle;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Initialize UI elements
        userName = findViewById(R.id.user_name);
        userEmail = findViewById(R.id.user_email);
        defaultAddress = findViewById(R.id.default_address);
        orderHistory = findViewById(R.id.order_history);
        profilePicture = findViewById(R.id.profile_picture);
        manageAddresses = findViewById(R.id.manage_addresses);
        managePaymentMethods = findViewById(R.id.manage_payment_methods);
        contactSupport = findViewById(R.id.contact_support);
        logoutButton = findViewById(R.id.logout_button);
        deactivateButton = findViewById(R.id.deactivate_button);
        loginButton = findViewById(R.id.login_button);
        notificationToggle = findViewById(R.id.notification_toggle);

        // Load user details
        boolean isLoggedIn = loadUserDetails();

        // Show or hide buttons based on login status
        if (!isLoggedIn) {
            logoutButton.setEnabled(false);
            logoutButton.setBackgroundTintList(getResources().getColorStateList(R.color.disabled_button));
            loginButton.setVisibility(View.VISIBLE); // Show login button
        } else {
            loginButton.setVisibility(View.GONE); // Hide login button
        }

        // Login button logic
        loginButton.setOnClickListener(v -> {
            Intent intent = new Intent(profile.this, login.class);
            startActivity(intent);
        });

        // Logout button logic
        logoutButton.setOnClickListener(v -> {
            logoutUser();
        });

        // Manage Addresses button logic
        manageAddresses.setOnClickListener(v -> {
            Toast.makeText(this, "Manage Addresses clicked!", Toast.LENGTH_SHORT).show();
        });

        // Manage Payment Methods button logic
        managePaymentMethods.setOnClickListener(v -> {
            Toast.makeText(this, "Manage Payment Methods clicked!", Toast.LENGTH_SHORT).show();
        });

        // Contact Support button logic
        contactSupport.setOnClickListener(v -> {
            Toast.makeText(this, "Contact Support clicked!", Toast.LENGTH_SHORT).show();
        });

        deactivateButton.setOnClickListener(v -> {
            onDeactivateClick();
        });
    }

    private boolean loadUserDetails() {
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            return false;  // User is not logged in
        }

        // Retrieve user details from Firestore or SharedPreferences (whichever you're using)
        String name = currentUser.getDisplayName() != null ? currentUser.getDisplayName() : "Guest";
        String email = currentUser.getEmail() != null ? currentUser.getEmail() : "";
        String address = ""; // Fetch address from Firestore or SharedPreferences
        String orders = "";  // Fetch order history from Firestore or SharedPreferences

        // Update UI with user details
        userName.setText(name);
        userEmail.setText(email);
        defaultAddress.setText(address);
        orderHistory.setText(orders);

        // Set a default profile picture
        profilePicture.setImageResource(R.drawable.user_icon);

        return true;  // User is logged in
    }


    private void logoutUser() {
        // Clear user details from SharedPreferences
        SharedPreferences preferences = getSharedPreferences("UserDetails", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();

        // Redirect to login activity
        Intent intent = new Intent(profile.this, login.class);
        startActivity(intent);
        finish();
    }

    public void onDeactivateClick() {
        // Show a confirmation dialog
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to deactivate your account?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> deactivateAccount())
                .setNegativeButton("No", null)
                .show();
    }


    private void deactivateAccount() {
        String userId = mAuth.getCurrentUser().getUid();  // Get the current user's ID

        db.collection("iceCreamy")  // Replace "users" with your collection name
                .document(userId)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    // Account deleted from Firestore
                    Toast.makeText(this, "Your account has been deactivated.", Toast.LENGTH_SHORT).show();

                    // Delete the user from Firebase Authentication
                    deleteFirebaseUser();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed to deactivate account. Try again.", Toast.LENGTH_SHORT).show();
                });
    }

    private void deleteFirebaseUser() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            Toast.makeText(this, "No user is logged in.", Toast.LENGTH_SHORT).show();
            return;
        }

        user.delete()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // User account deleted successfully
                        Toast.makeText(this, "Account deleted successfully.", Toast.LENGTH_SHORT).show();

                        // Optionally, log the user out and redirect to the login screen
                        mAuth.signOut();
                        Intent intent = new Intent(profile.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(this, "Failed to delete account. Try again.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
