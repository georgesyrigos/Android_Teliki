package com.example.android_teliki_v2;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UserActivity extends AppCompatActivity implements LocationListener {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private LocationManager locationManager;
    private String currentLocationName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        // Retrieve the username from the intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("username")) {
            String username = intent.getStringExtra("username");

            // Display the username in a TextView
            TextView textViewUsername = findViewById(R.id.textView19);
            if (textViewUsername != null) {
                textViewUsername.setText("Welcome " + username+"!");
            }
        }

        // Initialize locationManager
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (locationManager == null) {
            // Handle the case where LocationManager couldn't be initialized
            Toast.makeText(this, "Location Manager initialization failed", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity if LocationManager is null
            return;
        }

        // Request location updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 123);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }

    }

    public void logout(View view){
        FirebaseAuth.getInstance().signOut(); // Sign out the user
        startActivity(new Intent(UserActivity.this, LoginActivity.class)); // Redirect to LoginActivity
        finish(); // Close current activity
    }
    public void addEvent(View view){
        startActivity(new Intent(UserActivity.this, NewEventActivity.class)); // Redirect to LoginActivity
        finish(); // Close current activity
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        // Get current location
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        // Convert coordinates to location name
        Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);
        currentLocationName = getLocationFromCoordinates(latitude, longitude, geocoder);

        // Perform further processing or checks here
        // For example, check for dangerous events in the vicinity
        checkForDangerousEvents();
        locationManager.removeUpdates(this);

    }

    private void checkForDangerousEvents() {
        // Implement logic to check for dangerous events based on the current location
        // You can use the currentLocationName or latitude/longitude as needed
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference eventsRef = db.collection("events");

        // Get the current date
        Calendar currentDate = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");


        // Query for events with situation "confirmed" and matching location
        db.collection("events")
                .whereEqualTo("Situation", "confirmed")
                .whereEqualTo("Location", currentLocationName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String eventType = document.getString("Event");
                                String eventLocation = document.getString("Location");
                                String timestamp = document.getString("Timestamp");
                                String additionalInfo = document.getString("Comment");
                                if ("Fire".equals(eventType)){
                                    additionalInfo = "Get as far as you can from the fire!";
                                }
                                else if ("Earthquake".equals(eventType)){
                                    additionalInfo = "Get away from any building and stay outside!";
                                }
                                else if ("Flood".equals(eventType)){
                                    additionalInfo = "Get to a tall and stable building!";
                                }
                                try {
                                    Date eventDate = dateFormat.parse(timestamp);
                                    Calendar eventCalendar = Calendar.getInstance();
                                    eventCalendar.setTime(eventDate);

                                    // Check if the event is on the same day as the current date
                                    if (isSameDay(currentDate, eventCalendar)) {
                                        // Show the message with event details
                                        showMessage("You are in danger",
                                                "Event Type: " + eventType + "\n" +
                                                "Location: " + eventLocation + "\n" +
                                                "Timestamp: " + timestamp + "\n" +
                                                "Additional Information: " + additionalInfo);
                                        return; // Exit loop after displaying the message once
                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            Toast.makeText(UserActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private String getLocationFromCoordinates(double latitude, double longitude, Geocoder geocoder) {
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                String cityName = address.getLocality();
                if (cityName != null && !cityName.isEmpty()) {
                    return cityName;
                } else {
                    return "Unnamed Location";
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Location not found";
    }


    private void showMessage(String Title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                //new AlertDialog.Builder(this)
                .setTitle(Title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // Helper method to check if two Calendar instances represent the same day
    private boolean isSameDay(Calendar calendar1, Calendar calendar2) {
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) &&
                calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH) &&
                calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH);
    }
}