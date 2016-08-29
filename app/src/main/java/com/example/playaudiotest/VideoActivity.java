package com.example.playaudiotest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;

public class VideoActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int MY_PERMISSION_READ_STOREAGE = 100;
    private Button play;
    private Button pause;
    private Button replay;
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        play = (Button)findViewById(R.id.play);
        pause = (Button)findViewById(R.id.pause);
        replay = (Button)findViewById(R.id.replay);

        videoView = (VideoView)findViewById(R.id.video_view);

        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        replay.setOnClickListener(this);

        if(checkAndRequestPermission())
            initVideoPath();
    }

    private void initVideoPath(){
        File file = new File(Environment.getExternalStorageDirectory(), "timber.mp4");
        videoView.setVideoPath(file.getPath());

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.play:
                if(!videoView.isPlaying())
                    videoView.start();
                break;
            case R.id.pause:
                if(videoView.isPlaying())
                    videoView.pause();
                break;
            case R.id.replay:
                if(videoView.isPlaying())
                    videoView.resume();
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_READ_STOREAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    Toast.makeText(this, "permission is granted successfully", Toast.LENGTH_SHORT).show();
                    initVideoPath();
                } else {
                    // Permission Denied
                    Toast.makeText(this, "permission is denied", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }


    }

    private boolean checkAndRequestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Please grant this App the permission to READ EXTERNAL STORAGE", Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions( this,
                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSION_READ_STOREAGE );
            return false;
        }
        else
            return true;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(videoView!=null){
            videoView.suspend();
        }
    }
}
