package com.example.theicecreamy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class changepass extends AppCompatActivity {

    private EditText emailEditText;
    private Button resetPasswordButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepass);

        TextView login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(changepass.this, login.class);
                startActivity(intent);
            }
        });

        // Initialize FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        // Find views
        emailEditText = findViewById(R.id.email);
        resetPasswordButton = findViewById(R.id.sendmail);

        // Set the reset password button click listener
        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();

                if (email.isEmpty()) {
                    emailEditText.setError("Email is required");
                    emailEditText.requestFocus();
                    return;
                }

                // Call the method to send the password reset email
                sendPasswordResetEmail(email);
            }
        });
    }

    // Method to send password reset email
    private void sendPasswordResetEmail(String email) {
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(changepass.this, "Password reset email sent", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(changepass.this, login.class));
                    } else {
                        Toast.makeText(changepass.this, "Failed to send password reset email: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}
