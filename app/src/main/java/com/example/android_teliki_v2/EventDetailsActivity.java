package com.example.android_teliki_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class EventDetailsActivity extends AppCompatActivity {
    TextView tvEvent,tvUser,tvComment,tvLocation,tvTimestamp,tvPoints,tvSituation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        tvEvent = findViewById(R.id.tvEvent);
        tvUser = findViewById(R.id.tvUser);
        tvComment = findViewById(R.id.tvComment);
        tvLocation = findViewById(R.id.tvLocation);
        tvTimestamp = findViewById(R.id.tvTimestamp);
        tvPoints = findViewById(R.id.tvPoints);
        tvSituation = findViewById(R.id.tvSituation);

        // Get data from intent
        Event event = getIntent().getParcelableExtra("event");

        // Set data to TextViews
        if (event != null) {
            tvEvent.setText("Event: "+event.getEvent());
            tvUser.setText("Username: "+event.getUsername());
            tvComment.setText("Comment: "+event.getComment());
            tvLocation.setText("Location: "+event.getLocation());
            tvTimestamp.setText("Timestamp: "+event.getTimestamp());
            tvPoints.setText("Points: "+String.valueOf(event.getPoints()));
            tvSituation.setText("Situation: "+event.getSituation());
            // You can set other TextViews similarly
        }
    }

    public void back(View view){
        startActivity(new Intent(EventDetailsActivity.this, EmployeeActivity.class)); // Redirect to LoginActivity
        finish(); // Close current activity
    }
}