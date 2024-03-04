package com.example.android_teliki_v2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import javax.annotation.Nullable;

public class AppCompat extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        LanguageManager languageManager = new LanguageManager(this);
        languageManager.updateResource(languageManager.getLang());
    }
}
