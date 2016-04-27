package com.example.peter.popcornmovies;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.GridView;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Peter on 22/03/16.
 */

class InfoLoader extends AsyncTask<Void,Void,ArrayList<?>> {

    Context context;
    GridView gridView;
    int parsingChoice;
    String mainUrl;
    Parser parser;
    String JSONCode;

    static final int MOVIE_IMAGES=1;
    static final int MOVIE_TRAILERS=2;
    static final int MOVIE_REVIEWS=3;

    public InfoLoader(String mainUrl, Parser parser) {
        this.mainUrl = mainUrl;
        this.parser = parser;
        this.parsingChoice = parsingChoice;
        this.gridView = gridView;
    }

    public InfoLoader(Context c, String mainUrl, int parsingChoice) {
        this.mainUrl = mainUrl;
        this.parsingChoice = parsingChoice;
    }

    @Override
    protected ArrayList<?> doInBackground(Void... params) {

        try {
            URL url = new URL(mainUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder JSONCodeBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                JSONCodeBuilder.append(line + '\n');
            }
            JSONCode = JSONCodeBuilder.toString();

            return  parser.parse(JSONCode);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getJSONCode() {
        return JSONCode;
    }
}


