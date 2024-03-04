package com.example.android_teliki_v2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.List;

public class EmployeeActivity extends AppCompatActivity implements EventAdapter.OnItemClickListener{
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference eventsRef = db.collection("events");

    private EventAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        setUpRecyclerView();

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
    private void setUpRecyclerView() {
        Query query = eventsRef.orderBy("Points", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Event> options = new FirestoreRecyclerOptions.Builder<Event>()
                .setQuery(query, Event.class)
                .build();

        adapter = new EventAdapter(options);
        adapter.setOnItemClickListener(this); // Set the click listener


        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }



    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
        gradeEvents(); // Call the method to grade events when the activity starts
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    public void logout(View view){
        FirebaseAuth.getInstance().signOut(); // Sign out the user
        startActivity(new Intent(EmployeeActivity.this, LoginActivity.class)); // Redirect to LoginActivity
        finish(); // Close current activity
    }

    @Override
    public void onItemClick(String documentId, Event event) {
        Intent intent = new Intent(EmployeeActivity.this, EventDetailsActivity.class);
        intent.putExtra("eventId", documentId); // Pass the document ID to EventDetailsActivity
        intent.putExtra("event", event); // Pass clicked event to EventDetailsActivity
        startActivity(intent);
    }

    // Method to grade all events and update Firestore with calculated points
    private void gradeEvents() {
        eventsRef.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                Event event = documentSnapshot.toObject(Event.class);
                String documentId = documentSnapshot.getId(); // Retrieve the document ID
                int points = EventGrader.calculatePoints(event, queryDocumentSnapshots.toObjects(Event.class));
                eventsRef.document(documentId).update("Points", points);
            }
        }).addOnFailureListener(e -> {
            // Handle failure
        });
    }
}