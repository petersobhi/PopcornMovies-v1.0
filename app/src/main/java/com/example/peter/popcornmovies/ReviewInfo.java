package com.example.peter.popcornmovies;

/**
 * Created by Peter on 22/04/16.
 */
public class ReviewInfo {
    String author;
    String review;

    public ReviewInfo() {
    }

    public ReviewInfo(String author, String review) {
        this.author = author;
        this.review = review;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
