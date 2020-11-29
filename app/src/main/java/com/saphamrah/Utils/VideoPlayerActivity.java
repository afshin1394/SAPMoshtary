package com.saphamrah.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.devbrackets.android.exomedia.listener.OnPreparedListener;

import com.devbrackets.android.exomedia.ui.widget.VideoView;
import com.saphamrah.Application.BaseApplication;
import com.saphamrah.R;




public class VideoPlayerActivity extends AppCompatActivity {

    private VideoView videoView;
    private String hlsVideoUri = "";
    private String checkLink;

    @SuppressLint("ShowToast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exoplayer);
      Intent in = getIntent();
      Bundle linkVideo = in.getExtras();
      if (linkVideo != null) {
          hlsVideoUri = linkVideo.getString("LinkVideo");
          setupVideoView(hlsVideoUri);
      } else {
          Toast.makeText(BaseApplication.getContext() , getResources().getString(R.string.failPlayTizer), Toast.LENGTH_LONG);
      }



    }

    private void setupVideoView(String hlsVideoUri) {
        // Make sure to use the correct VideoView import
        videoView = findViewById(R.id.video_view);
        videoView.setOnPreparedListener(() -> videoView.start());

        //For now we just picked an arbitrary item to play
        videoView.setVideoURI(Uri.parse(hlsVideoUri));
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (videoView != null ){
            videoView.restart();
        }
    }
}