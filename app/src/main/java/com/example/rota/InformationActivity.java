package com.example.rota;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InformationActivity extends AppCompatActivity {
    EditText age;
    EditText favoritefood;
    EditText favoriteteam;
    EditText Jop;
    EditText Gender;
    Button save;
    String agename;
    String Jops;
    String Genders;
    String favoritefoods;
    String favoriteteams;

    FirebaseAuth mAuth;

    FirebaseUser user;

    DatabaseReference reference;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        age = findViewById(R.id.age);
        save = findViewById(R.id.save);
        Jop = findViewById(R.id.Jop);
        Gender = findViewById(R.id.Gender);

        favoritefood = findViewById(R.id.favoritefood);
        favoriteteam = findViewById(R.id.favoriteteam);

        //agetext = findViewById(R.id.agetext); ///لاظهار الداتا بصفحة

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agename = age.getText().toString();
                favoritefoods = favoritefood.getText().toString();
                favoriteteams = favoriteteam.getText().toString();
                Genders = Gender.getText().toString();
                Jops = Jop.getText().toString();

                if (TextUtils.isEmpty(agename)) {
                    age.setError("Required");
                } else if (TextUtils.isEmpty(favoritefoods)) {
                    favoritefood.setError("Required");
                } else if (TextUtils.isEmpty(favoriteteams)) {
                    favoriteteam.setError("Required");
                } else if (TextUtils.isEmpty(Genders)) {
                    Gender.setError("Required");
                } else if (TextUtils.isEmpty(Jops)) {
                    Jop.setError("Required");
                } else {




                    // Add data to Firebase database
                    reference.child("age").setValue(agename).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(InformationActivity.this, "Data saved successfully", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(InformationActivity.this, "Error saving data", Toast.LENGTH_SHORT).show();
                        }
                    });

                    reference.child("favoritefood").setValue(favoritefoods);
                    reference.child("favoriteteam").setValue(favoriteteams);
                    reference.child("Jop").setValue(Jops);
                    reference.child("Gender").setValue(Genders);
                }
            }
        });


// Read data from Firebase database
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String ageValue = dataSnapshot.child("age").getValue(String.class);
                    String favoriteFoodValue = dataSnapshot.child("favoritefood").getValue(String.class);
                    String favoriteTeamValue = dataSnapshot.child("favoriteteam").getValue(String.class);
                    String jopValue = dataSnapshot.child("Jop").getValue(String.class);
                    String genderValue = dataSnapshot.child("Gender").getValue(String.class);

                    age.setText(ageValue);
                    favoritefood.setText(favoriteFoodValue);
                    favoriteteam.setText(favoriteTeamValue);
                    Jop.setText(jopValue);
                    Gender.setText(genderValue);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(InformationActivity.this, "Error loading data", Toast.LENGTH_SHORT).show();
            }
        });



    }




}
