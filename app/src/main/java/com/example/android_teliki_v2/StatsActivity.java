package com.example.android_teliki_v2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class StatsActivity extends AppCompatActivity {
    private int fireCount = 0;
    private int floodCount = 0;
    private int earthquakeCount = 0;
    private int otherCount = 0;

    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Retrieve events data
        retrieveEventData();
    }

    private void retrieveEventData() {
        db.collection("events")
                .whereEqualTo("Situation", "confirmed")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        // Iterate through documents
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            // Check event type and update counts
                            String eventType = document.getString("Event");
                            if (eventType != null) {
                                switch (eventType.toLowerCase()) {
                                    case "fire":
                                        fireCount++;
                                        break;
                                    case "flood":
                                        floodCount++;
                                        break;
                                    case "earthquake":
                                        earthquakeCount++;
                                        break;
                                    case "other":
                                        otherCount++;
                                        break;
                                }
                            }
                        }
                        // Update TextViews with counts
                        updateTextViews();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    private void updateTextViews() {
        // Update TextViews with count values
        TextView fireTextView = findViewById(R.id.textView_fire);
        fireTextView.setText("Fire Events: " + fireCount );

        TextView floodTextView = findViewById(R.id.textView_flood);
        floodTextView.setText("Flood Events: " + floodCount);

        TextView earthquakeTextView = findViewById(R.id.textView_earthquake);
        earthquakeTextView.setText("Earthquake Events: " + earthquakeCount);

        TextView otherTextView = findViewById(R.id.textView_other);
        otherTextView.setText(" Other Events: " + otherCount);
    }
}