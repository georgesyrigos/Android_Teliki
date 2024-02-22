package com.example.android_teliki_v2;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class SignUpActivity extends AppCompatActivity {
    EditText mUsername,mPassword,mEmail,mRole;
    Button mSignupBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
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
        fStore = FirebaseFirestore.getInstance();



    }
    public void signup(View view){
        String username = mUsername.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        String email = mEmail.getText().toString().trim();
        String role = mRole.getText().toString().trim();

        if(TextUtils.isEmpty(username)) {
            mUsername.setError("Username is Required!");
        }
        fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener((task) -> {
            if (task.isSuccessful()){
                Toast.makeText(SignUpActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                userID = fAuth.getCurrentUser().getUid();
                DocumentReference documentReference = fStore.collection("users").document(userID);
                Map<String,Object> user = new HashMap<>();
                user.put("username",username);
                user.put("email",email);
                user.put("role",role);
                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                });
            }
        });

    }
}