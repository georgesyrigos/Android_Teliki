package com.example.android_teliki_v2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class NewEventActivity extends AppCompatActivity implements LocationListener {
    LocationManager locationManager;
    String x,y;
    EditText mUsername, mComment;
    Spinner mEvent; // Changed to Spinner
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String eventID;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);
        textView = findViewById(R.id.textView10);


        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        mUsername = findViewById(R.id.editTextText);
        mEvent = findViewById(R.id.EventSpinner);
        mComment = findViewById(R.id.editTextText2);

        // Set up Spinner with adapter containing user and employee choices
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.events_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mEvent.setAdapter(adapter);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
    }

    public void submit(View view) {
        String username = mUsername.getText().toString().trim();
        String event = mEvent.getSelectedItem().toString(); // Get selected role from Spinner
        String comment = mComment.getText().toString().trim();
        //String timestamp = Timestamp.now().toString();
        //Integer points = 0;
        //String situation = "pending";

        if (username.isEmpty() || comment.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        //give location info
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 123);
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);




    }



    //message that the user sees
    private void showMessage(String Title,String message){
        new AlertDialog.Builder(this)
                .setTitle(Title)
                .setMessage(message)
                .show();
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        //latitude
        x = String.valueOf(location.getLatitude());
        //longitude
        y = String.valueOf(location.getLongitude());
        //textView.setText(x);

    }
}