package com.example.android_teliki_v2;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class SignUpActivity extends AppCompatActivity {
    EditText mUsername,mPassword,mEmail;
    Spinner mRole; // Changed to Spinner
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
        mRole = findViewById(R.id.RoleSpinner);
        mSignupBtn = findViewById(R.id.signupButton);

        // Set up Spinner with adapter containing user and employee choices
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.roles_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mRole.setAdapter(adapter);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();



    }
    public void signup(View view){
        String username = mUsername.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        String email = mEmail.getText().toString().trim();
        String role = mRole.getSelectedItem().toString(); // Get selected role from Spinner

        if(TextUtils.isEmpty(username)) {
            mUsername.setError("Username is Required!");
            return;
        }
        if(TextUtils.isEmpty(password)) {
            mPassword.setError("Password is Required!");
            return;
        }
        if(TextUtils.isEmpty(email)) {
            mEmail.setError("Email is Required!");
            return;
        }
        if (TextUtils.isEmpty(role)) {
            Toast.makeText(this, "Role is Required!", Toast.LENGTH_SHORT).show();
            return;
        }
        fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener((task) -> {
            if (task.isSuccessful()) {
                Toast.makeText(SignUpActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                userID = fAuth.getCurrentUser().getUid();
                DocumentReference documentReference = fStore.collection("users").document(userID);
                Map<String, Object> user = new HashMap<>();
                user.put("username", username);
                user.put("email", email);
                user.put("role", role);
                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        // Redirect the user based on role
                        if (role.equals("user")) {
                            Intent intent = new Intent(SignUpActivity.this, UserActivity.class); // Change to your desired activity
                            intent.putExtra("username", username);
                            startActivity(intent);
                            finish(); // Finish the SignUpActivity so user cannot go back to it
                        } else if (role.equals("employee")) {
                            Intent intent = new Intent(SignUpActivity.this, EmployeeActivity.class); // Change to your desired activity
                            intent.putExtra("username", username);
                            startActivity(intent);
                            finish(); // Finish the SignUpActivity so user cannot go back to it
                        } else {
                            Toast.makeText(SignUpActivity.this, "Unknown role: " + role, Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });











    }

}