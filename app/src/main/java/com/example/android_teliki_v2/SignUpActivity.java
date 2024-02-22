package com.example.android_teliki_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


public class SignUpActivity extends AppCompatActivity {
    EditText mUsername,mPassword,mEmail,mRole;
    Button mSignupBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mUsername = findViewById(R.id.UsernameText);
        mPassword = findViewById(R.id.PasswordText);
        mEmail = findViewById(R.id.EmailAddressText);
        mRole = findViewById(R.id.RoleText);
        mSignupBtn = findViewById(R.id.signupButton);

        fAuth = FirebaseAuth.getInstance();
    }
}