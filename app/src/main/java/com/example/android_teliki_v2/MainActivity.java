package com.example.android_teliki_v2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompat {
    TextView textView4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button el = findViewById(R.id.btn_el);
        Button en = findViewById(R.id.btn_en);
        LanguageManager lang = new LanguageManager(this);
        en.setOnClickListener(view ->{
            lang.updateResource("en");
            recreate();
        });
        el.setOnClickListener(view ->{
            lang.updateResource("el");
            recreate();
        });

        textView4 = findViewById(R.id.textView4);
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 123);
            return;
        }





    }
    public void login(View view){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
    }

    public void signup(View view){
        Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    public void stats(View view){
        Intent intent = new Intent(MainActivity.this, StatsActivity.class);
        startActivity(intent);
    }





}

