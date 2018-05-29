package cn.jzvd.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.WindowManager;

/**
 * Created by admin on 11/28/2017.
 */

public class AboutApp extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_app);

        int m = AppCompatDelegate.getDefaultNightMode();

        if(m==AppCompatDelegate.MODE_NIGHT_YES) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.screenBrightness = Float.parseFloat("0.2");
            getWindow().setAttributes(lp);
        }

    }
}
