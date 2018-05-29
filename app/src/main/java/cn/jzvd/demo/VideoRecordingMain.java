package cn.jzvd.demo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.VideoView;

/**
 * Created by Nikita on 26-11-2017.
 */

public class VideoRecordingMain extends Activity{
    private Button mRecordView,mPlayView;
    private VideoView mVideoView;
    private int ACTIVITY_START_CAMERA_APP=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_recording_main);

        int m = AppCompatDelegate.getDefaultNightMode();

        if(m==AppCompatDelegate.MODE_NIGHT_YES) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.screenBrightness = Float.parseFloat("0.2");
            getWindow().setAttributes(lp);
        }

        mRecordView =(Button) findViewById(R.id.recordButton);
        //mPlayView =(Button) findViewById(R.id.playButton);
        //mVideoView =(VideoView) findViewById(R.id.videoView);

        mRecordView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent callVideoAppIntent = new Intent();
                callVideoAppIntent.setAction(MediaStore.ACTION_VIDEO_CAPTURE);
                startActivityForResult(callVideoAppIntent,ACTIVITY_START_CAMERA_APP);
            }
        });

//        mPlayView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {//               mVideoView.start();
            }
//        });
//    }
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if(requestCode == ACTIVITY_START_CAMERA_APP && requestCode == RESULT_OK){
//            Uri videoUri = data.getData();              //HERE IS THE VIDEO BEING CALLED AFTER RECORDING AND BEING PLAYED IN THE VIDEOVIEW
//            mVideoView.setVideoURI(videoUri);
//        }
//    }
}


