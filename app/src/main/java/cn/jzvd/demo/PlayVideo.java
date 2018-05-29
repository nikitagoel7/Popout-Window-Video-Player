package cn.jzvd.demo;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import cn.jzvd.*;

import static cn.jzvd.demo.NewRecyclerView.arr;

/**
 * Created by admin on 11/25/2017.
 */

public class PlayVideo extends AppCompatActivity implements View.OnClickListener {


    String path;
    VideoView vv;
    MediaController ms;
    ImageView po;

    int yy1;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);

        int m = AppCompatDelegate.getDefaultNightMode();

        if(m==AppCompatDelegate.MODE_NIGHT_YES) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.screenBrightness = Float.parseFloat("0.2");
            getWindow().setAttributes(lp);
        }
        System.gc();
        ms = new MediaController(this);
        Intent ii = getIntent();
        Bundle extras=ii.getExtras();
        setContentView(R.layout.activity_play_video);
        vv = (VideoView) findViewById(R.id.videoWatch);
        po = (ImageView) findViewById(R.id.popout);

        po.setOnClickListener(this);
        String yy = extras.getString("value");
        path = yy;
        // System.out.print(yy);
        //  path = yy;
        vv.setVideoPath(yy);
        // System.out.print(yy);
        vv.setMediaController(ms);
        //System.out.print(yy);
        vv.requestFocus();
        //System.out.print(yy);
        FloatingWindow.vv=path;

        vv.start();
        //System.out.print(yy);
        vv.setOnCompletionListener
                (
                        new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                if(yy1<arr.size()-1)
                                {
                                    yy1=yy1+1;
                                    String s=NewRecyclerView.arr.get(yy1).getdata();
                                    vv.setVideoPath(s);
                                    FloatingWindow.vv=s;
                                    vv.setMediaController(ms);
                                    vv.requestFocus();
                                    vv.start();
                                }
                                if(yy1==arr.size()-1)
                                {
                                    yy1=0;
                                    String s=NewRecyclerView.arr.get(yy1).getdata();
                                    vv.setVideoPath(s);
                                    FloatingWindow.vv=s;
                                    vv.setMediaController(ms);
                                    vv.requestFocus();
                                    vv.start();

                                }
                            }
                        }
                );
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.popout)
        {
            Context context = getApplicationContext();
            FloatingWindow.m = vv.getCurrentPosition();
            Intent intent = new Intent(context, FloatingWindow.class);
            context.startService(intent);
            finish();
        }
    }
}