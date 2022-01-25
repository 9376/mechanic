package com.example.mymechanic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://my-mechanic-5c6e5-default-rtdb.firebaseio.com/");

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        final EditText phone = findViewById(R.id.phone);
        final EditText Password = findViewById(R.id.password);
        final Button loginbtn = findViewById(R.id.loginbtn);
        final TextView registerNow = findViewById(R.id.registerNow);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String phoneTxt = phone.getText().toString();
                final String passwordTxt = Password.getText().toString();

                if (phoneTxt.isEmpty() || passwordTxt.isEmpty()){
                    android.widget.Toast.makeText(Login.this, "Please Enter your Phone or Password", Toast.LENGTH_SHORT).show();
                }
                else {

                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            //check if email exists in the firebase
                            if (snapshot.hasChild(phoneTxt)){

                                //if email exists in the firebase DB
                                //get password from the DB and match with the password entered

                                final String getPassword = snapshot.child(phoneTxt).child("password").getValue(String.class);

                                if (getPassword.equals(passwordTxt)){
                                    Toast.makeText(Login. this, "Login Successful", Toast.LENGTH_SHORT).show();

                                    //Open main activity on success
                                    startActivity(new Intent(Login.this, MainActivity.class));
                                    finish();
                                }
                                else {
                                    Toast.makeText(Login.this, "Wrong Password", Toast.LENGTH_SHORT).show();

                                }
                            }

                            else {
                                Toast.makeText(Login.this, "Wrong Phone Number", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        });

        registerNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //open register activity
                startActivity(new Intent(Login.this, Register.class));
            }
        });
    }
}
