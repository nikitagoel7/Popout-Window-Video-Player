package cn.jzvd.demo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by admin on 11/28/2017.
 */

public class YoutubeSearch extends AppCompatActivity {
    String q;
    EditText mGet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.youtube_search);


        int m = AppCompatDelegate.getDefaultNightMode();

        if(m==AppCompatDelegate.MODE_NIGHT_YES) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.screenBrightness = Float.parseFloat("0.2");
            getWindow().setAttributes(lp);
        }

        Button go = (Button) findViewById(R.id.button);

        mGet = (EditText) findViewById(R.id.editText);

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                q = mGet.getText().toString();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/results?search_query=" + q));
                startActivity(intent);
            }
        });

    }
}