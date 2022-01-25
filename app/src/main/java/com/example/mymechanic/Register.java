package com.example.mymechanic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ButtonBarLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {

    //create object of DatabaseReference class to access firebase's realtime Database
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://my-mechanic-5c6e5-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText fullname = findViewById(R.id.fullname);
        final EditText phone = findViewById(R.id.phone);
        final EditText password = findViewById(R.id.password);
        final EditText conpassword = findViewById(R.id.conpassword);

        final Button registerbtn = findViewById(R.id.registerbtn);
        final TextView loginnowbtn = findViewById(R.id.loginNow);

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //gets data from EditTexts into String variables
                final String fullnameTxt = fullname.getText().toString();
                final String phoneTxt = phone.getText().toString();
                final String passwordTxt = password.getText().toString();
                final String conpasswordTxt = conpassword.getText().toString();

                //check if the user fills in all the fields before sending data to firebase
                if (fullnameTxt.isEmpty() || phoneTxt.isEmpty() ||  passwordTxt.isEmpty()){
                    Toast.makeText(Register.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }

                //check if passwords match
                //if they are not matching show a toast message
                else if (!passwordTxt.equals(conpasswordTxt)){
                    Toast.makeText(Register.this, "Passwords don't match!!", Toast.LENGTH_SHORT).show();
                }

                else {

                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            //check if email is not registered before
                            if (snapshot.hasChild(phoneTxt)){
                                Toast.makeText(Register.this, "Phone Already Registered!", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                //sending data to firebase Realtime DB
                                //we are using email as a unique identity of every user
                                //so the other details of user comes under email address
                                databaseReference.child("users").child(phoneTxt).child("fullname").setValue(fullnameTxt);
                                databaseReference.child("users").child(phoneTxt).child("phone").setValue(phoneTxt);
                                databaseReference.child("users").child(phoneTxt).child("password").setValue(passwordTxt);

                                //show a success message then finish the activity
                                Toast.makeText(Register.this, "User Registered Successfully!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }
            }
        });

        loginnowbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}
