package com.example.theicecreamy;

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
import com.google.firebase.auth.UserProfileChangeRequest;

public class signup extends AppCompatActivity {

    private EditText name, email1, password1, confirmpass;
    private Button SignUp;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch adminSwitch;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        // Find views
        name = findViewById(R.id.name);
        email1 = findViewById(R.id.email);
        password1 = findViewById(R.id.password);
        confirmpass = findViewById(R.id.confirmpass);
        SignUp = findViewById(R.id.signb);
        adminSwitch = findViewById(R.id.adminButton); // Admin switch

        TextView login = findViewById(R.id.member);
        login.setOnClickListener(v -> {
            Intent intent = new Intent(signup.this, login.class);
            startActivity(intent);
        });

        SignUp.setOnClickListener(v -> {
            String email = email1.getText().toString().trim();
            String password = password1.getText().toString().trim();
            String confirmPassword = confirmpass.getText().toString().trim();

            if (email.isEmpty()) {
                email1.setError("Email is required");
                email1.requestFocus();
                return;
            }
            if (password.isEmpty()) {
                password1.setError("Password is required");
                password1.requestFocus();
                return;
            }
            if (password.length() < 6) {
                password1.setError("Password must be at least 6 characters");
                password1.requestFocus();
                return;
            }
            if (!password.equals(confirmPassword)) {
                password1.setError("Passwords do not match");
                password1.requestFocus();
                return;
            }

            signUpUser(email, password);
        });
    }

    private void signUpUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();

                        if (user != null) {
                            String userName = name.getText().toString().trim();
                            boolean isAdmin = adminSwitch.isChecked();

                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(userName)
                                    .build();

                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(profileTask -> {
                                        if (profileTask.isSuccessful()) {
                                            // Save admin status to SharedPreferences
                                            SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString("USERNAME", userName);
                                            editor.putBoolean("isLoggedIn", true);
                                            editor.putBoolean("isAdmin", isAdmin);
                                            editor.apply();

                                            Toast.makeText(signup.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();

                                            // Redirect based on admin switch
                                            if (isAdmin) {
                                                startActivity(new Intent(signup.this, admin_dashboard.class));
                                            } else {
                                                startActivity(new Intent(signup.this, MainActivity.class));
                                            }
                                            finish();
                                        } else {
                                            Toast.makeText(signup.this, "Failed to set display name: " + profileTask.getException().getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    });
                        }
                    } else {
                        Toast.makeText(signup.this, "Sign Up Failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}
