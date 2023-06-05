package com.example.rota;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class CatActivity extends AppCompatActivity {
    private ImageView mImageView;
    private Button catstart;
    private ImageButton mPlayButton2;
    private MediaPlayer mMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat);


        TextView songName = findViewById(R.id.song_name_textview);

// تحريك التحول
        TranslateAnimation animation = new TranslateAnimation(-100f, 0f, 0f, 0f);
        animation.setDuration(2000); // المدة في الأمتار المكعبة
        songName.startAnimation(animation);

// الظهور التدريجي
        AlphaAnimation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setDuration(5000); // المدة في الأمتار المكعبة
        songName.startAnimation(fadeIn);



        mImageView = findViewById(R.id.imageView);
        catstart = findViewById(R.id.catstart);
        mPlayButton2 = findViewById(R.id.playpausecat);

        // تشغيل الأغنية عند الدخول إلى الأكتفتي
        mMediaPlayer = MediaPlayer.create(this, R.raw.shemeels);
        mMediaPlayer.start();







catstart.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent =new Intent(CatActivity.this, Petactivity.class);
        startActivity(intent);
    }
});

        mPlayButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.pause();
                    mPlayButton2.setImageResource(R.drawable.baseline_play_circle_24);
                } else {
                    mMediaPlayer.start();
                    mPlayButton2.setImageResource(R.drawable.baseline_pause_circle_24);
                }
            }
        });
    }


    @Override
    protected void onStop() {
        super.onStop();
        // إيقاف الأغنية عند الانتقال إلى أكتفتي أخرى
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }
}