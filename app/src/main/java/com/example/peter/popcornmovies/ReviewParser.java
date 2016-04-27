package com.example.peter.popcornmovies;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Peter on 16/04/16.
 */
public class ReviewParser implements Parser {

    @Override
    public ArrayList<ReviewInfo> parse(String JSONCode) throws JSONException {
        JSONObject allPage = new JSONObject(JSONCode);
        JSONArray results= allPage.getJSONArray("results");
        ArrayList<ReviewInfo> reviews = new ArrayList<>();

        if(results.length() == 0){
            ReviewInfo review = new ReviewInfo();
            review.setAuthor("No Reviews");
            reviews.add(review);
        }
        else {
            for (int i = 0; i < results.length(); i++) {
                ReviewInfo review = new ReviewInfo();
                review.setAuthor(results.getJSONObject(i).getString("author"));
                review.setReview(results.getJSONObject(i).getString("content"));
                reviews.add(review);
            }
        }
        return reviews;
    }
}
