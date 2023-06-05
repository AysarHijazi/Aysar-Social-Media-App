package com.example.rota;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.VideoView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;


public class ReelsFragment extends Fragment {
    ViewPager2 viewPager2;
    VideoAdapter adapter;
    VideoView videoView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reels, container, false);



        viewPager2 = view.findViewById(R.id.vpager);

        FirebaseRecyclerOptions<VideoModel> options =
                new FirebaseRecyclerOptions.Builder<VideoModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("video"), VideoModel.class)
                        .build();

        adapter=new VideoAdapter(options);
        viewPager2.setAdapter(adapter);





        return view;

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (adapter != null) {
                adapter.startVideo();
            }
        } else {
            if (adapter != null) {
                adapter.stopVideo();
            }
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (videoView != null) {
            videoView.stopPlayback();
            videoView.setOnCompletionListener(null);
            videoView.setOnPreparedListener(null);
            videoView.setVideoURI(null);
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        if (videoView != null) {
            videoView.stopPlayback();

        }
    }


}
