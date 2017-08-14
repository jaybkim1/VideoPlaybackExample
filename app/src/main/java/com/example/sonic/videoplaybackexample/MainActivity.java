package com.example.sonic.videoplaybackexample;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
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

    private ArrayList<String> mVideoPath;
    private ArrayList<String> mVideoList;
    private ArrayList<Bitmap> mVideoThumnails;

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

        // Create thumbnail from .mp4 that is stored in raw folder
        createThumbnails();

        mVideoView = (VideoView) findViewById(R.id.videoView);
        mMediaController = new MediaController(this);
        mVideoView.setMediaController(mMediaController);
        mVideoView.setVideoURI(mUri);
        mVideoView.start();
    }


    private void listVideosFromRawFolder() {

        mVideoPath = new ArrayList<>();
        mVideoList = new ArrayList<>();

        Field[] fields = R.raw.class.getFields();
        for (int count = 0; count < fields.length; count++) {

            try {
                mResourceID = fields[count].getInt(fields[count]);
                mFileName = fields[count].getName();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            mVideoPath.add("android.resource://" + getPackageName() + "/"+mResourceID);
            mVideoList.add(mFileName);

        }
    }

    private void createThumbnails(){

        mVideoThumnails = new ArrayList<>();

        // To see how many files there are in raw folder
        for (int i = 0; i < mVideoList.size(); i++) {

            Log.i(TAG,"mVideoPath:"+ mVideoPath);
            Log.i(TAG,"mVideoList:"+ mVideoList);

            // How to create a thumbnail (bitmap)
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();

            mUri = Uri.parse(mVideoPath.get(i));
            retriever.setDataSource(this, mUri);
            Bitmap bitmap = retriever.getFrameAtTime(100000, MediaMetadataRetriever.OPTION_PREVIOUS_SYNC);
            mVideoThumnails.add(bitmap);
        }
    }

}