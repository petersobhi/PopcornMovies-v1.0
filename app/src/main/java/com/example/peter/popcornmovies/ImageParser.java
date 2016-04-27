package com.example.peter.popcornmovies;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**
 * Created by Peter on 20/03/16.
 */
public class ImageParser implements Parser  {

    @Override
    public ArrayList<String> parse(String JSONCode) throws JSONException {
        JSONObject allPage = new JSONObject(JSONCode);
        JSONArray results= allPage.getJSONArray("results");
        ArrayList<String> imagesURLs = new ArrayList<>();
        for (int i = 0; i < 20 ; i++) {
            imagesURLs.add("http://image.tmdb.org/t/p/w300/" + results.getJSONObject(i).getString("poster_path"));
        }
        return imagesURLs;
    }
}
