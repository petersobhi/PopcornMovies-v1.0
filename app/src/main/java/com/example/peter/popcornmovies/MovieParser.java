package com.example.peter.popcornmovies;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Peter on 13/04/16.
 */
public class MovieParser  {

    int movieNum;

    public MovieParser(int movieNum) {
        this.movieNum = movieNum;
    }

    public static MovieInfo parseAll(String JSONCode , int movieNum) throws JSONException {
        MovieInfo movie = new MovieInfo();
        JSONObject allPage = new JSONObject(JSONCode);
        JSONArray results= allPage.getJSONArray("results");
        JSONObject movieInfo = results.getJSONObject(movieNum);
        movie.setTitle(movieInfo.getString("title"));
        movie.setImageURL("http://image.tmdb.org/t/p/w300/" + movieInfo.getString("poster_path"));
        movie.setOverview(movieInfo.getString("overview"));
        movie.setReleaseDate(movieInfo.getString("release_date"));
        movie.setRating(movieInfo.getDouble("vote_average"));
        movie.setId(movieInfo.getInt("id"));
        return movie;
    }

    public static MovieInfo parseAllFav(String JSONCodeP , String JSONCodeTR , String imageURL) throws JSONException {
        MovieInfo movie = new MovieInfo();
        String imagePath = imageURL.replace("http://image.tmdb.org/t/p/w300/", "");

        if (JSONCodeP != null) {
            Log.e("parseAllFav: ","Entered JSON Popular" );
            JSONObject allPageP = new JSONObject(JSONCodeP);
            JSONArray resultsP = allPageP.getJSONArray("results");
            for (int i = 0; i < resultsP.length(); i++) {
                JSONObject movieInfo = resultsP.getJSONObject(i);
                Log.e("parseAllFav: ", movieInfo.getString("poster_path"));
                if (imagePath.equals(movieInfo.getString("poster_path"))) {
                    movie.setTitle(movieInfo.getString("title"));
                    movie.setImageURL("http://image.tmdb.org/t/p/w300/" + movieInfo.getString("poster_path"));
                    movie.setOverview(movieInfo.getString("overview"));
                    movie.setReleaseDate(movieInfo.getString("release_date"));
                    movie.setRating(movieInfo.getDouble("vote_average"));
                    movie.setId(movieInfo.getInt("id"));
                }
            }
        }

        if (JSONCodeTR != null) {
            Log.e("parseAllFav: ","Entered JSON Top Rated" );
            JSONObject allPageTR = new JSONObject(JSONCodeTR);
            JSONArray resultsTR = allPageTR.getJSONArray("results");
            for (int i = 0; i < resultsTR.length(); i++) {
                JSONObject movieInfo = resultsTR.getJSONObject(i);
                Log.e("parseAllFav: ", movieInfo.getString("poster_path"));
                if (imagePath.equals(movieInfo.getString("poster_path"))) {
                    movie.setTitle(movieInfo.getString("title"));
                    movie.setImageURL("http://image.tmdb.org/t/p/w300/" + movieInfo.getString("poster_path"));
                    movie.setOverview(movieInfo.getString("overview"));
                    movie.setReleaseDate(movieInfo.getString("release_date"));
                    movie.setRating(movieInfo.getDouble("vote_average"));
                    movie.setId(movieInfo.getInt("id"));
                }
            }
        }
            return movie;
        }

}
