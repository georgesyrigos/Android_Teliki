package com.example.android_teliki_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class EmployeeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        // Retrieve the username from the intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("username")) {
            String username = intent.getStringExtra("username");

            // Display the username in a TextView
            TextView textViewUsername = findViewById(R.id.textView21);
            if (textViewUsername != null) {
                textViewUsername.setText("Welcome " + username+"!");
            }
        }
    }
    public void logout(View view){
        FirebaseAuth.getInstance().signOut(); // Sign out the user
        startActivity(new Intent(EmployeeActivity.this, LoginActivity.class)); // Redirect to LoginActivity
        finish(); // Close current activity
    }
}