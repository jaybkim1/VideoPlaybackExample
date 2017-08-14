package com.example.sonic.videoplaybackexample;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    private VideoView mVideoView;
    private MediaController mMediaController;

    private ArrayList<Integer> mVideoResId;
    private ArrayList<String> mVideoList;

    private Uri mUri;
    private int mResourceID;
    private String mFileName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);


        // Put all the names of video files and resource id to mVideoResId and mVideoList
        listVideosFromRawFolder();

        // To see how many files there are in raw folder
        for (int i = 0; i < mVideoList.size(); i++) {
            Log.i(TAG,"mVideoResId:"+ mVideoResId);
            Log.i(TAG,"mVideoList:"+ mVideoList);
        }

        // You can implement this sample project to have a dialogue to choose which one to play and convert that URI and pass it to setVideoURI()
        mUri = Uri.parse("android.resource://" + getPackageName() + "/" + mVideoResId.get(0)); // android.resource://com.example.sonic.videoplaybackexample/2131099648(R.raw.sample)


        mVideoView = (VideoView) findViewById(R.id.videoView);
        mMediaController = new MediaController(this);
        mVideoView.setMediaController(mMediaController);
        mVideoView.setVideoURI(mUri);
        mVideoView.start();
    }


    private void listVideosFromRawFolder() {

        mVideoResId = new ArrayList<>();
        mVideoList = new ArrayList<>();

        Field[] fields = R.raw.class.getFields();
        for (int count = 0; count < fields.length; count++) {

            try {
                mResourceID = fields[count].getInt(fields[count]);
                mFileName = fields[count].getName();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            mVideoResId.add(mResourceID);
            mVideoList.add(mFileName);

        }
    }
}