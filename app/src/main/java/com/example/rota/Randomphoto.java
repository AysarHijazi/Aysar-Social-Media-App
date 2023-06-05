package com.example.rota;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

public class Randomphoto extends AppCompatActivity {

        private ImageView mImageView;
        private int[] mImages = {
                R.drawable.person1,
                R.drawable.person2,
                R.drawable.person3,
                R.drawable.person4,
                R.drawable.person5,



        };

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_randomphoto);

            mImageView = findViewById(R.id.imageView2);

            Bundle extras = getIntent().getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("image");

            if (imageBitmap != null) {
                mImageView.setImageBitmap(imageBitmap);
            }

            int randomIndex = (int) (Math.random() * mImages.length);
            int drawableId = mImages[randomIndex];
            mImageView.setImageResource(drawableId);

            Toast.makeText(this, "This person looks like you!", Toast.LENGTH_SHORT).show();
        }
    }
