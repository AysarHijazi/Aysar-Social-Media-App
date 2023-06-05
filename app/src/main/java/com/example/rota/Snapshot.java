package com.example.rota;

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

import androidx.appcompat.app.AppCompatActivity;

public class Snapshot extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private Bitmap mImageBitmap;
    private ImageView mImageView;
    private Button mButton;
    private ImageButton mPlayButton;
    private MediaPlayer mMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snapshot);


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
        mButton = findViewById(R.id.takephoto);
        mPlayButton = findViewById(R.id.playpause);

        // تشغيل الأغنية عند الدخول إلى الأكتفتي
        mMediaPlayer = MediaPlayer.create(this, R.raw.love);
        mMediaPlayer.start();







        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.pause();
                    mPlayButton.setImageResource(R.drawable.baseline_play_circle_24);
                } else {
                    mMediaPlayer.start();
                    mPlayButton.setImageResource(R.drawable.baseline_pause_circle_24);
                }
            }
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            mImageBitmap = (Bitmap) extras.get("data");
            Intent intent = new Intent(this, Randomphoto.class);
            intent.putExtra("image", mImageBitmap);
            startActivity(intent);
        }
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