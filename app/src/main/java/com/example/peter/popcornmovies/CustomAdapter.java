package com.example.peter.popcornmovies;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by Peter on 19/03/16.
 */
public class CustomAdapter extends BaseAdapter {
    ArrayList<String> imagesURLs;
    Context context;
    public CustomAdapter(Context context, ArrayList<String> imagesURLs){
        //super(context,R.layout.grid_item,imagesURLs);
        this.context = context;
        this.imagesURLs = imagesURLs;
    }


    @Override
    public int getCount() {
        return imagesURLs.size();
    }

    @Override
    public Object getItem(int position) {
        return imagesURLs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    class ImageViewHolder
    {
        ImageView myImage;
        ImageViewHolder(View v)
        {
            myImage = (ImageView) v.findViewById(R.id.grid_movieImage);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridItem = convertView;
        ImageViewHolder viewHolder;
        if(gridItem == null){
           LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridItem = inflater.inflate(R.layout.grid_item,parent,false);
            viewHolder = new ImageViewHolder(gridItem);
            gridItem.setTag(viewHolder);
        }
        else {
             viewHolder = (ImageViewHolder) gridItem.getTag();
        }
        /*
        int width = context.getResources().getDisplayMetrics().widthPixels/2;
        int height = (int) (width* 1.6);*/
        Picasso.with(context).load(imagesURLs.get(position)).into(viewHolder.myImage);
        Log.e("getView: ","View Created"+position );
        return gridItem;
    }
}
