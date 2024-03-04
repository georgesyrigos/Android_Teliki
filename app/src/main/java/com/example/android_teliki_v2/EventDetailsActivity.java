package com.example.android_teliki_v2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class EventDetailsActivity extends AppCompatActivity {
    TextView tvEvent,tvUser,tvComment,tvLocation,tvTimestamp,tvPoints,tvSituation;
    // Declare eventId
    private String eventId;
    // Firebase
    FirebaseFirestore db = FirebaseFirestore.getInstance();

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

        // Retrieve eventId from intent extras
        eventId = getIntent().getStringExtra("eventId");

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

    public void confirmEvent(View view) {
        // Get a reference to the event document in Firestore
        DocumentReference eventRef = db.collection("events").document(eventId);

        // Update the "Situation" field of the event document to "confirmed"
        eventRef.update("Situation", "confirmed")
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(EventDetailsActivity.this, "Event confirmed", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent( EventDetailsActivity.this ,EmployeeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EventDetailsActivity.this, "Failed to confirm event: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void denyEvent(View view) {
        // Get a reference to the event document in Firestore
        DocumentReference eventRef = db.collection("events").document(eventId);

        // Update the "Situation" field of the event document to "confirmed"
        eventRef.update("Situation", "denied")
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(EventDetailsActivity.this, "Event denied", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent( EventDetailsActivity.this ,EmployeeActivity.class);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EventDetailsActivity.this, "Failed to confirm event: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    public void back(View view){
        startActivity(new Intent(EventDetailsActivity.this, EmployeeActivity.class)); // Redirect to LoginActivity
        finish(); // Close current activity
    }
}