package com.example.rota;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplachScreenWelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splachscreen);



        Animation animtext = new AlphaAnimation(0.0f, 1.0f);
        animtext.setDuration(2000);
        TextView titlename = findViewById(R.id.titlename);
        titlename.startAnimation(animtext);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent Intent = new Intent(SplachScreenWelcomeActivity.this, LoginActivity.class);
                startActivity(Intent);
                finish();
            }
        },3000);

    }
}