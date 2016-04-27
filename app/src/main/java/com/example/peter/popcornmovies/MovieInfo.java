package com.example.peter.popcornmovies;

import java.text.DecimalFormat;

/**
 * Created by Peter on 21/03/16.
 */
public class MovieInfo {
    private String title;
    private String imageURL;
    private String overview;
    private double rating;
    private String releaseDate;
    private int id;


    public MovieInfo(String title, int id, String imageURL, String overview, double rating, String releaseDate) {
        this.title = title;
        this.id = id;
        this.imageURL = imageURL;
        this.overview = overview;
        this.rating = rating;
        this.releaseDate = releaseDate;
    }

    public MovieInfo() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRating() {
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        Double formattedRating = Double.parseDouble(decimalFormat.format(rating));
        return formattedRating.toString();
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getYear(){
        if(releaseDate==null)
            return null;
        return releaseDate.substring(0, 4);
    }
}
