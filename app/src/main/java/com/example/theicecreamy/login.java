package com.example.theicecreamy;

import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch adminSwitch;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        // Find views
        emailEditText = findViewById(R.id.name);
        passwordEditText = findViewById(R.id.Password);
        loginButton = findViewById(R.id.login);
        adminSwitch = findViewById(R.id.adminButton); // Admin switch

        // Login button click listener
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                // Validate inputs
                if (email.isEmpty()) {
                    emailEditText.setError("Email is required");
                    emailEditText.requestFocus();
                    return;
                }
                if (password.isEmpty()) {
                    passwordEditText.setError("Password is required");
                    passwordEditText.requestFocus();
                    return;
                }
                loginUser(email, password);
            }
        });

        // Sign up button click listener
        Button signupButton = findViewById(R.id.signup);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, signup.class);
                startActivity(intent);
            }
        });

        // Forgot password click listener
        TextView forgotpass = findViewById(R.id.forgotpass);
        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, changepass.class);
                startActivity(intent);
            }
        });
    }

    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            String username = user.getDisplayName(); // Get the user's display name
                            // Save username and login status to SharedPreferences
                            SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("USERNAME", username);
                            editor.putBoolean("isLoggedIn", true); // Mark user as logged in
                            editor.putBoolean("isAdmin", adminSwitch.isChecked()); // Save admin status
                            editor.apply();

                            Toast.makeText(login.this, "Login Successful", Toast.LENGTH_SHORT).show();

                            // Check if the switch is on for admin
                            if (adminSwitch.isChecked()) {
                                // Redirect to Admin Dashboard
                                startActivity(new Intent(login.this, admin_dashboard.class));
                            } else {
                                // Redirect to User Dashboard
                                startActivity(new Intent(login.this, MainActivity.class));
                            }
                            finish();  // Optional: Close login activity after successful login
                        }
                    } else {
                        Toast.makeText(login.this, "Login Failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}
