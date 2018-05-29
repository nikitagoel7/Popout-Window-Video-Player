package cn.jzvd.demo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by admin on 11/25/2017.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder> implements View.OnCreateContextMenuListener
{
    int videoColumnIndex;
    Context con;


    static  int recyPosition;
    public VideoAdapter(Context con)
    {
        this.con=con;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View myView= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler,parent,false);

        myView.setOnCreateContextMenuListener(this);
        return new MyViewHolder(myView);
    }

    @Override

    public void onBindViewHolder(MyViewHolder holder, final int position)
    {

        String s1=NewRecyclerView.arr.get(position).getName();

        String s2=NewRecyclerView.arr.get(position).getdata();
        holder.txt.setText(s1);
        holder.img.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                System.out.print("hellojbc");
                Intent ii=new Intent(con.getApplicationContext(),PlayVideo.class);
                ii.putExtra("value",NewRecyclerView.arr.get(position).getdata());
                ii.putExtra("value1",position);
                con.startActivity(ii);
            }
        });
        holder.txt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                System.out.print("hellojbc");
                Intent ii=new Intent(con.getApplicationContext(),PlayVideo.class);
                ii.putExtra("value",NewRecyclerView.arr.get(position).getdata());
                ii.putExtra("value1",position);
                con.startActivity(ii);
            }
        });
        try
        {
            byte [] raw;
            Bitmap art;
            MediaMetadataRetriever mmr=new MediaMetadataRetriever();
            mmr.setDataSource(con,Uri.parse(s2));
            art=mmr.getFrameAtTime(3000);
            holder.img.setImageBitmap(art);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        /*holder.img.setOnClickListener(new 0View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent ii=new Intent(con.getApplicationContext(),PlayVideo.class);
                con.startActivity(ii);
                ii.putExtra("value",MainActivity.arr.get(position).getdata());
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return NewRecyclerView.arr.size();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        menu.setHeaderTitle("Select an Option:");
        menu.add(0,v.getId(),0,"Delete");
        menu.add(0,v.getId(),0,"Share");
        menu.add(0,v.getId(),0,"Play this in loop");
        menu.add(0,v.getId(),0,"Find on Google");
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements AdapterView.OnLongClickListener
    {
        ImageView img;
        TextView txt;
        public MyViewHolder(View view)
        {
            super(view);
            img=(ImageView)view.findViewById(R.id.imgView);
            txt=(TextView)view.findViewById(R.id.txtView);
            txt.setOnLongClickListener(this);
            img.setOnLongClickListener(this);
            view.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View v)
        {
            recyPosition=getAdapterPosition();
            System.out.println(recyPosition);
            return false;
        }
    }
}
