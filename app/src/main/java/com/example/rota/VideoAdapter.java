package com.example.rota;

import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;
import java.util.List;

public class VideoAdapter extends FirebaseRecyclerAdapter<VideoModel,VideoAdapter.myviewholder>
{

    public VideoAdapter(@NonNull FirebaseRecyclerOptions<VideoModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull VideoModel model) {
        holder.setdata(model);
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_video_row,parent,false);
        return new myviewholder(view);
    }
    private MediaPlayer mediaPlayer;


    class myviewholder extends RecyclerView.ViewHolder
    {
        VideoView videoView;
        TextView title,desc;
        ProgressBar pbar;

        public myviewholder(@NonNull View itemView)
        {
            super(itemView);

            videoView=(VideoView)itemView.findViewById(R.id.videoView);
            title=(TextView)itemView.findViewById(R.id.textVideoTitle);
            desc=(TextView)itemView.findViewById(R.id.textVideoDescription);
            pbar=(ProgressBar)itemView.findViewById(R.id.videoProgressBar);
        }

        void setdata(VideoModel obj)
        {
            videoView.setVideoPath(obj.getUrl());
            title.setText(obj.getTitle());
            desc.setText(obj.getDesc());

            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    pbar.setVisibility(View.GONE);
                    mediaPlayer = mp;
                }
            });

            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                }
            });

            // Add a click listener to the videoView to pause the video when clicked
            videoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (videoView.isPlaying()) {
                        videoView.pause();
                    } else {
                        videoView.start();
                    }
                }
            });
        }
    }

}

