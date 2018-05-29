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
import android.widget.VideoView;

import static cn.jzvd.demo.NewRecyclerView.arr;

/**
 * Created by admin on 12/1/2017.
 */

public class PlayVideoLoop  extends AppCompatActivity implements View.OnClickListener {


    String path;
    VideoView vv;
    MediaController ms;
    ImageView po;

    int yy1;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        int m = AppCompatDelegate.getDefaultNightMode();

        if (m == AppCompatDelegate.MODE_NIGHT_YES) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.screenBrightness = Float.parseFloat("0.2");
            getWindow().setAttributes(lp);
        }
        System.gc();
        ms = new MediaController(this);
        Intent ii = getIntent();
        Bundle extras = ii.getExtras();
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
        FloatingWindow.vv = path;

        vv.start();

        vv.setOnCompletionListener
                (
                        new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                    FloatingWindow.vv=path;
                                    vv.requestFocus();
                                    vv.seekTo(0);
                                    vv.start();

                            }
                        }
                );
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.popout)
        {
            Context context = getApplicationContext();
            //CharSequence text = "Wait a bit more time";
            //int duration = Toast.LENGTH_SHORT;

            //Toast toast = Toast.makeText(context, text, duration);
            //toast.show();
            FloatingWindow.m = vv.getCurrentPosition();
            Intent intent = new Intent(context, FloatingWindow.class);
            context.startService(intent);
            finish();
        }
    }
}
