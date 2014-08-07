package com.uk.braiko.subtexter;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;
import java.net.URL;


public class MyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        FixedVideoView videoView = (FixedVideoView) findViewById(R.id.view);
        videoView.setVideoURI(Uri.parse("http://freedroid.net/video/test.mp4"));
        videoView.start();
        try {
            videoView.setSubtitleSource(new URL("http://freedroid.net/tdo/Sabotage.2014.1080p-720p.BluRay.x264.YIFY.Eng.srt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        videoView.setSubTitleView((android.widget.TextView) findViewById(R.id.subtitle));
        videoView.seekTo(49999);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
