package com.example.peter.popcornmovies;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Peter on 15/04/16.
 */
public class TrailerParser implements Parser{


    @Override
    public ArrayList<TrailerInfo> parse(String JSONCode) throws JSONException {
        JSONObject allPage = new JSONObject(JSONCode);
        JSONArray results= allPage.getJSONArray("results");
        ArrayList<TrailerInfo> trailers = new ArrayList<>();
        for (int i = 0; i < results.length() ; i++) {
            TrailerInfo trailer = new TrailerInfo();
            trailer.setName(results.getJSONObject(i).getString("name"));
            trailer.setKey(results.getJSONObject(i).getString("key"));
            trailers.add(trailer);
        }
        return trailers;
    }
}
