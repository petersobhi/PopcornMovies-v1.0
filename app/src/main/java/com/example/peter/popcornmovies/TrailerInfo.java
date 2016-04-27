package com.example.peter.popcornmovies;

/**
 * Created by Peter on 22/04/16.
 */
public class TrailerInfo {
    private String name;
    private String key;

    public TrailerInfo() {
    }

    public TrailerInfo(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
