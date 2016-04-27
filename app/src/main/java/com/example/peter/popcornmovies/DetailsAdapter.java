package com.example.peter.popcornmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.Inflater;

/**
 * Created by Peter on 22/04/16.
 */
public class DetailsAdapter extends BaseExpandableListAdapter {
    Context context;
    ArrayList<TrailerInfo> trailers;
    ArrayList<ReviewInfo> reviews;

    public DetailsAdapter(Context context, ArrayList<TrailerInfo> trailers, ArrayList<ReviewInfo> reviews) {
        this.context = context;
        this.trailers = trailers;
        this.reviews = reviews;
    }

    @Override
    public int getGroupCount() {
        return 2;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        switch (groupPosition){
            case 0: return trailers.size();
            case 1: return reviews.size();
            default: return 0;
        }
    }

    @Override
    public ArrayList<?> getGroup(int groupPosition) {
        switch (groupPosition){
            case 0: return trailers;
            case 1: return reviews;
            default: return null;
        }
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        switch (groupPosition){
            case 0: return trailers.get(childPosition);
            case 1: return reviews.get(childPosition);
            default: return null;
        }
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View groupView = convertView;
        if(groupView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            groupView = inflater.inflate(R.layout.details_group,parent,false);
        }
        TextView groupLabel = (TextView) groupView.findViewById(R.id.details_group_text);
        switch (groupPosition){
            case 0: groupLabel.setText("Trailers"); break;
            case 1: groupLabel.setText("Reviews"); break;
        }
        return groupView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View itemView = convertView;
        /*
        if(itemView == null){

            switch (groupPosition){
                case 0: itemView = inflater.inflate(R.layout.details_trailer_item,parent,false); break;
                case 1: itemView = inflater.inflate(R.layout.details_review_item,parent,false); break;
            }
        }
        */
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        switch (groupPosition){
            case 0: //trailer view
                itemView = inflater.inflate(R.layout.details_trailer_item,parent,false);
                TextView trailerLabel = (TextView) itemView.findViewById(R.id.details_trailer_label);
                ImageView trailerImage = (ImageView) itemView.findViewById(R.id.details_trailer_image);
                TrailerInfo trailer = trailers.get(childPosition);
                trailerLabel.setText(trailer.getName());
                Picasso.with(context).load("http://img.youtube.com/vi/"+trailer.getKey()+"/0.jpg").into(trailerImage);
                break;
            case 1: //review view
                itemView = inflater.inflate(R.layout.details_review_item,parent,false);
                TextView reviewAuthor = (TextView) itemView.findViewById(R.id.details_review_author);
                ExpandableTextView reviewText = (ExpandableTextView) itemView.findViewById(R.id.details_review_extext);
                ReviewInfo review = reviews.get(childPosition);
                reviewAuthor.setText(review.getAuthor());
                reviewText.setText(review.getReview());
                break;
        }

        return itemView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }





}
