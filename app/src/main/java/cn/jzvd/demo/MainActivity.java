package cn.jzvd.demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

/**
 * Created by Nathen on 16/7/22.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener{//,ViewAnimator.ViewAnimatorListener {


    Button mTinyWindow, mAutoTinyWindow, mAboutListView, mPlayDirectly, mAboutApi, mAboutWebView, mnightmode;
    FloatingActionButton floatingActionButton;
    private Context mContext;
    private int brightnessValue =12;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_ACTION_BAR);

        mContext = getApplicationContext();

        int m = AppCompatDelegate.getDefaultNightMode();

        if(m!=AppCompatDelegate.MODE_NIGHT_YES){

            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.screenBrightness = Float.parseFloat("0.2");
            getWindow().setAttributes(lp);

        }
        else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mnightmode = (Button) findViewById(R.id.night_mode);
        mTinyWindow = (Button) findViewById(R.id.tiny_window);
        mAutoTinyWindow = (Button) findViewById(R.id.auto_tiny_window);
        mPlayDirectly = (Button) findViewById(R.id.search_youtube);
        mAboutListView = (Button) findViewById(R.id.about_listview);
        mAboutApi = (Button) findViewById(R.id.about_api);
        mAboutWebView = (Button) findViewById(R.id.about_webview);
        floatingActionButton= (FloatingActionButton) findViewById(R.id.floatingActionButton);

        mnightmode.setOnClickListener(this);
        mTinyWindow.setOnClickListener(this);
        mAutoTinyWindow.setOnClickListener(this);
        mAboutListView.setOnClickListener(this);
        mPlayDirectly.setOnClickListener(this);
        mAboutApi.setOnClickListener(this);
        mAboutWebView.setOnClickListener(this);
        floatingActionButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tiny_window:
                FloatingWindow.m=0;
                FloatingWindow.vv="android.resource://" + getPackageName() + "/" + R.raw.big_buck_bunny;
                Context context = getApplicationContext();
                Intent intent = new Intent(context, FloatingWindow.class);
                context.startService(intent);
                break;
            case R.id.search_youtube:
                startActivity(new Intent(MainActivity.this, YoutubeSearch.class));
                break;
            case R.id.about_listview:
                startActivity(new Intent(MainActivity.this, NewRecyclerView.class));
                break;
            case R.id.about_webview:
                startActivity(new Intent(MainActivity.this, NewGridView.class));
                break;
            case R.id.about_api:
                startActivity(new Intent(MainActivity.this, AboutApp.class));
                break;
            case R.id.night_mode:
                Intent intent1= new Intent(MainActivity.this, MainActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
                break;
            case R.id.floatingActionButton:
                startActivity(new Intent(MainActivity.this,VideoRecordingMain.class));
                break;
        }
    }

}
