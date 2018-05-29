package cn.jzvd;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.IBinder;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

/**
 * Created by admin on 10/5/2017.
 */

public class FloatingWindow extends Service {

    private WindowManager wm;
    private LinearLayout ll;
    private RelativeLayout rl;
    private Button stop;
    private VideoView videoView;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        wm=(WindowManager)getSystemService(WINDOW_SERVICE);
        ll= new LinearLayout(this);
        rl= new RelativeLayout(this);

        stop= new Button(this);
        videoView = new VideoView(this);
        LinearLayout.LayoutParams btnParameters = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        stop.setText("X");
        stop.setLayoutParams(btnParameters);

        LinearLayout.LayoutParams vV = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        //vV.setMargins(140,140,40,40);
        videoView.setLayoutParams(vV);


        MediaController mc=new MediaController(this);
        String uripath="android.resource://"+getPackageName()+"/";//+R.raw.vd;
        Uri video= Uri.parse(uripath);        videoView.setMediaController(mc);

        videoView.setVideoURI(video);

        LayoutParams llParameters = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        ll.setBackgroundColor(Color.TRANSPARENT );//Color.argb(66, 255,0,0));
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setLayoutParams(llParameters);

        LayoutParams rlParameters = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        rl.setLayoutParams(rlParameters);

        final WindowManager.LayoutParams parameters = new WindowManager.LayoutParams(702,531,WindowManager.LayoutParams.TYPE_PHONE,WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);
        parameters.x =0;
        parameters.y =0;
        parameters.gravity = Gravity.CENTER | Gravity.CENTER;


        ll.addView(stop);

        ll.addView(videoView);

        wm.addView(ll,parameters);
        videoView.start();

        ll.setOnTouchListener(new View.OnTouchListener() {
            private WindowManager.LayoutParams updatedParameters = parameters;
            int x , y;
            float touchedX, touchedY;

            @Override
            public boolean onTouch(View arg0, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x=updatedParameters.x;
                        y=updatedParameters.y;

                        touchedX=event.getRawX();
                        touchedY=event.getRawY();
                        break;

                    case MotionEvent.ACTION_MOVE:
                        updatedParameters.x=(int)(x+(event.getRawX()-touchedX));
                        updatedParameters.y=(int)(y+(event.getRawY()-touchedY));

                        wm.updateViewLayout(ll,updatedParameters);
                    default:
                        break;

                }
                return false;
            }
        } );
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wm.removeView(ll);
                Toast.makeText(getBaseContext(), "Comming Soon", Toast.LENGTH_SHORT).show();

                stopSelf();
            }

        });

        videoView.setOnTouchListener(new View.OnTouchListener() {

            private WindowManager.LayoutParams updatedParameters = parameters;
            int x , y;
            float touchedX, touchedY;

            private int CLICK_ACTION_THRESHOLD = 200;
            private float startX;
            private float startY;
            boolean isMoved;



            @Override
            public boolean onTouch(View arg0, MotionEvent event) {

                switch(event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        x=updatedParameters.x;
                        y=updatedParameters.y;
                        isMoved = false;

                        touchedX=event.getRawX();
                        touchedY=event.getRawY();
                        startX = event.getX();
                        startY = event.getY();
                        break;

                    case MotionEvent.ACTION_MOVE:
                        updatedParameters.x=(int)(x+(event.getRawX()-touchedX));
                        updatedParameters.y=(int)(y+(event.getRawY()-touchedY));
                        wm.updateViewLayout(ll,updatedParameters);
                        isMoved = true;

                        break;
                    case MotionEvent.ACTION_UP:

                        if (!isMoved) {
                            JZVideoPlayerStandard.startFullscreen(getBaseContext(), JZVideoPlayerStandard.class, VideoConstant.videoUrlList[6], "嫂子辛苦了");

                            wm.removeView(ll);
//
                            stopSelf();
//                            if(videoView.isPlaying())
//                            {
//                                videoView.pause();
//                            }
//                        else
//                                videoView.resume();
//                        }
                            break;
                        }



                }
//
//                if(videoView.isPlaying()&&videoView.canPause())
//                {
//                    videoView.pause();
//                }
//                else
//                    videoView.resume();
//                return false;
                //wm.removeView(ll);
                //stopSelf();
                return true;
            }

            private boolean isAClick(float startX, float endX, float startY, float endY) {
                float differenceX = Math.abs(startX - endX);
                float differenceY = Math.abs(startY - endY);
                return !(differenceX > CLICK_ACTION_THRESHOLD/* =5 */ || differenceY > CLICK_ACTION_THRESHOLD);
            }
        });

        videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if(videoView.isPlaying()&&videoView.canPause())
//                {
//                    videoView.pause();
//                }
//                else
//                    videoView.resume();


            }

        });
    }
}