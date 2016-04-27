package com.example.peter.popcornmovies;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Peter on 13/04/16.
 */
public interface Parser {
    ArrayList<?> parse(String JSONCode) throws JSONException;
}
