package com.example.rota;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UplodActivity extends AppCompatActivity {

    private ProgressBar processBars;
    private VideoView videoView;
    private Uri videouris;
    MediaController mediaController;
    private StorageReference mstorageref;
    Button uploadbtn;
    Button choosebuttons;

    private static final int PICK_Vide = 1;
    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uplod);

        choosebuttons = findViewById(R.id.choose);
        uploadbtn = findViewById(R.id.upload);
        videoView = findViewById(R.id.videoviews);
        processBars = findViewById(R.id.progress);

        mediaController = new MediaController(this);
        mstorageref = FirebaseStorage.getInstance().getReference("videos");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("video"); // إنشاء مرجع لقاعدة البيانات الحية
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);
        videoView.start();

        choosebuttons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosesvideo();
            }
        });

        uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadvideos();
            }
        });
    }

    private void choosesvideo() {
        Intent intents = new Intent();
        intents.setType("video/*");
        intents.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intents, PICK_Vide);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_Vide && resultCode == RESULT_OK && data != null && data.getData() != null) {
            videouris = data.getData();
            videoView.setVideoURI(videouris);
        }
    }

    private String getfileextensions(Uri videouris) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypess = MimeTypeMap.getSingleton();
        return mimeTypess.getExtensionFromMimeType(contentResolver.getType(videouris));
    }

    private void uploadvideos() {
        processBars.setVisibility(View.VISIBLE);
        if (videouris != null) {
            StorageReference references = mstorageref.child(System.currentTimeMillis() + "." + getfileextensions(videouris));
            references.putFile(videouris)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            processBars.setVisibility(View.INVISIBLE);
                            Toast.makeText(UplodActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            // get the URL of the uploaded video
                            references.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String videoUrl = uri.toString();
                                    // add the URL to the "video" folder in Realtime Database
                                    DatabaseReference videoRef = FirebaseDatabase.getInstance().getReference().child("video").child("v" + System.currentTimeMillis());
                                    videoRef.child("url").setValue(videoUrl).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(UplodActivity.this, "Video URL added to Realtime Database", Toast.LENGTH_SHORT).show();


                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(UplodActivity.this, "Failed to add Video URL to Realtime Database", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            processBars.setVisibility(View.INVISIBLE);
                            Toast.makeText(UplodActivity.this, "failed", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(UplodActivity.this, "no file selected", Toast.LENGTH_SHORT).show();
        }
    }
}


