package cn.jzvd.demo;

/**
 * Created by admin on 11/25/2017.
 */

import android.app.SearchManager;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SeekBar;


import java.io.File;
import java.util.ArrayList;

public class NewRecyclerView extends AppCompatActivity
{

    static ArrayList<VideoDetails>arr;
    VideoAdapter videoAdapter;
    RecyclerView rec;
    public Cursor cur;
    SeekBar brrightnes;

    private int brightness;
    private ContentResolver cResolver;
    private Window window;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.new_view);

        int m = AppCompatDelegate.getDefaultNightMode();

        if(m==AppCompatDelegate.MODE_NIGHT_YES) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.screenBrightness = Float.parseFloat("0.2");
            getWindow().setAttributes(lp);
        }

/*
        setupBrightness();
*/

        // registerForContextMenu(rec);
        brrightnes= (SeekBar) findViewById(R.id.seekbarBrightness);
        brrightnes.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                // Toast.makeText(MainActivity.this, ""+seekBar.getProgress(), Toast.LENGTH_SHORT).show();
                float hey =(float)seekBar.getProgress()/(float)100 *(float)255;
                Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, (int)hey);
                WindowManager.LayoutParams layoutpars = getWindow().getAttributes();
                layoutpars.screenBrightness = hey;
                getWindow().setAttributes(layoutpars);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //seekBar.getProgress();

/*
                Toast.makeText(MainActivity.this, ""+seekBar.getProgress(), Toast.LENGTH_SHORT).show();
                float hey =(float)seekBar.getProgress()/(float)100 *(float)255;
                Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, (int)hey);
                WindowManager.LayoutParams layoutpars = getWindow().getAttributes();
                layoutpars.screenBrightness = hey;
                getWindow().setAttributes(layoutpars);*/


            }
        });
        arr = new ArrayList<>();
        getVideoData();

        videoAdapter=new VideoAdapter(this);

        rec=(RecyclerView)findViewById(R.id.recy);

        rec.setAdapter(videoAdapter);

          rec.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
//this is grid view
        //rec.setLayoutManager(new GridLayoutManager(getApplicationContext(),4,GridLayoutManager.VERTICAL, false));
        rec.addItemDecoration(new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL));

    }

    private void setupBrightness() {
        //Get the content resolver
        cResolver = getContentResolver();

//Get the current window
        window = getWindow();

        try
        {
            // To handle the auto
            Settings.System.putInt(cResolver,
                    Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
            //Get the current system brightness
            brightness = Settings.System.getInt(cResolver, Settings.System.SCREEN_BRIGHTNESS);
        }
        catch (Settings.SettingNotFoundException e)
        {
            //Throw an error case it couldn't be retrieved
            Log.e("Error", "Cannot access system brightness");
            e.printStackTrace();
        }
    }

    public void getVideoData()
    {
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

        //String s1=MediaStore.Video.Media.
        String[] videoProjection =
                {MediaStore.Video.Media._ID,
                        MediaStore.Video.Media.DATA,
                        MediaStore.Video.Media.DISPLAY_NAME,
                        MediaStore.Video.Media.SIZE
                };
        Cursor cur = this.managedQuery(uri, videoProjection, null, null, null);
        try {
            cur.moveToFirst();
        } catch (Exception e) {
            e.printStackTrace();
        }
        while (cur.moveToNext())
        {
            VideoDetails vid = new VideoDetails(cur.getString(0), cur.getString(1), cur.getString(2), cur.getString(3));
            arr.add(vid);
        }
        //Toast.makeText(getApplicationContext(), arr.size() + " ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        if(item.getTitle().equals("Share"))
        {
            int pos=VideoAdapter.recyPosition;
            String path=arr.get(pos).getdata();
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_STREAM,Uri.fromFile(new File(path)));
            intent.setType("video/mp4");
            startActivity(intent);
        }
        if(item.getTitle().equals("Delete"))
        {
            int pos=VideoAdapter.recyPosition;
            String path=arr.get(pos).getdata();
            arr.remove(pos);

            File ff=new File(path);
            ff.delete();

            videoAdapter.notifyItemRemoved(pos);
            videoAdapter.notifyDataSetChanged();

        }
        if(item.getTitle().equals("Find on Google"))
        {
            int pos=VideoAdapter.recyPosition;
            String q=arr.get(pos).getName();

            Intent intent = new Intent(Intent.ACTION_WEB_SEARCH );
            intent.putExtra(SearchManager.QUERY,q);
            startActivity(intent);
        }
        if(item.getTitle().equals("Play this in loop"))
        {
            int pos=VideoAdapter.recyPosition;
            Intent ii=new Intent(getApplicationContext(),PlayVideoLoop.class);
            ii.putExtra("value",NewRecyclerView.arr.get(pos).getdata());
            ii.putExtra("value1",pos);
            startActivity(ii);
        }


        return super.onContextItemSelected(item);
    }
}

