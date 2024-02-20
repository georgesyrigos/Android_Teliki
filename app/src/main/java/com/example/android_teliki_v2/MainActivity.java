package com.example.android_teliki_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView textView4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView4 = findViewById(R.id.textView4);
        textView4.setText("Notification for the nearest dangers\n\nFloods, Fires, Earthquakes, Other natural disasters");

    }
    public void login(View view){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
    }

    public void signup(View view){
        Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    public class world{

    }
}

