package com.bhuvanesh.talenthive.sports.fragment;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bhuvanesh.talenthive.BaseActivity;
import com.bhuvanesh.talenthive.BaseFragment;
import com.bhuvanesh.talenthive.R;

import life.knowledge4.videotrimmer.K4LVideoTrimmer;
import life.knowledge4.videotrimmer.interfaces.OnTrimVideoListener;

public class VideoTrimmerFragment extends BaseFragment   implements OnTrimVideoListener{

    private Uri videoUri;
    public K4LVideoTrimmer videoTrimmer;
    public static VideoTrimmerFragment newInstance(Uri uri){
        VideoTrimmerFragment videoTrimmerFragment=new VideoTrimmerFragment();
        videoTrimmerFragment.videoUri=uri;
        return videoTrimmerFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_video_trimmer,container,false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        videoTrimmer= (K4LVideoTrimmer) view.findViewById(R.id.timeLine);
        getActivity().setTitle("Video Trimmer");
        if(((BaseActivity)getActivity()).getSupportActionBar()!=null)
        {
            ((BaseActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if (videoTrimmer!=null){
            videoTrimmer.setMaxDuration(10);
                         videoTrimmer.setOnTrimVideoListener(this);
                         videoTrimmer.setDestinationPath("/storage/emulated/0/DCIM/CameraCustom/");
                         //videoTrimmer.setVideoURI(Uri.fromFile(new File("/storage/sdcard/video/VID-20160411-WA0007.mp4")));
                         videoTrimmer.setVideoURI(videoUri);
           // videoTrimmer.setVideoInformationVisibility(true);
        }

    }

    @Override
    public void getResult(Uri uri) {

    }

    @Override
    public void cancelAction() {
        videoTrimmer.destroy();
    }
}
