package com.bhuvanesh.talenthive.dance.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;

import com.bhuvanesh.talenthive.BaseFragment;
import com.bhuvanesh.talenthive.R;
import com.bhuvanesh.talenthive.dance.adapter.DanceFeedAdapter;
import com.bhuvanesh.talenthive.dance.model.Dance;
import com.bhuvanesh.talenthive.storywriting.adapter.StoryFeedAdapter;
import com.bhuvanesh.talenthive.util.THLoggerUtil;

import java.util.ArrayList;
import java.util.List;

public class DanceFeedFragment extends BaseFragment {

    private DanceFeedAdapter mDanceFeedAdapter;

    private MediaController mediaControls;
    private static final int PICK_GALLERY_VIDEO_REQUEST_CODE = 10;
//    private VideoView videoView;
    private int position;

    public static DanceFeedFragment newInstance() {
        return new DanceFeedFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dance_feed, container, false);

//        videoView = (VideoView) view.findViewById(R.id.videoview);
        if (mediaControls == null)
            mediaControls = new MediaController(getActivity());
//        videoView.setMediaController(mediaControls);
//        videoView.setVideoURI(Uri.parse("https://m.youtube.com/watch?v=UGULcNSNtHM"));

        Button buttonVideoFile = (Button) view.findViewById(R.id.button_video_file);
        buttonVideoFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_GALLERY_VIDEO_REQUEST_CODE);
            }
        });

        List<String> danceList = new ArrayList<>();
        danceList.add("content://media/external/video/media/176233");
        danceList.add("content://media/external/video/media/255666");
        danceList.add("content://media/external/video/media/162817");
        danceList.add("content://media/external/video/media/284131");
        danceList.add("content://media/external/video/media/284103");


        RecyclerView recyclerViewDance = (RecyclerView) view.findViewById(R.id.recyclerview_dance_feed);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewDance.setLayoutManager(layoutManager);

        if (mDanceFeedAdapter == null)
            mDanceFeedAdapter = new DanceFeedAdapter();
        recyclerViewDance.setAdapter(mDanceFeedAdapter);
        mDanceFeedAdapter.setData(danceList);
        recyclerViewDance.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

            }
        });


        /*videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
//                to repeat the video again & again
                mp.start();
                mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mp.setLooping(true);
            }
        });*/
        return view;
    }

    /*@Override
    public void onPause() {
        super.onPause();

        if (videoView != null) {
            position = videoView.getCurrentPosition();
            videoView.pause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (videoView != null) {
            videoView.seekTo(position);
            videoView.start();
        }
    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_GALLERY_VIDEO_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            THLoggerUtil.println("video path = " + data.getData().toString());
//            videoView.setVideoPath(data.getData().toString());
        }
    }
}
