package com.example.rota;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

public class PetDetailsActivity extends AppCompatActivity {
    TextView petName;
    TextView petPrice;
    TextView petPhone;
    ImageView petImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_details);

        petName = findViewById(R.id.petName);
        petPrice = findViewById(R.id.petPrice);
        petPhone = findViewById(R.id.petPhone);
        petImage = findViewById(R.id.petImage);

        Intent intent = getIntent();
        String petJson = intent.getStringExtra("pet");
        Pet pet = new Gson().fromJson(petJson, Pet.class);

        petName.setText(pet.getName());
        petPrice.setText(pet.getPrice());
        petPhone.setText(pet.getPhone());
        Glide.with(this).load(pet.getImageUrl()).into(petImage);
    }
}