package com.example.peter.popcornmovies;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetalisFragment extends Fragment implements iObserverFragment {


    Intent intent;
    View rootView;
    ViewGroup groupHeader;
    String movieTitle;
    String movieOverview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_details,menu);
        /*
        MenuItem shareButton = menu.findItem(R.id.details_share);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.putExtra(Intent.EXTRA_TEXT,"Popcorn Movies: "+movieTitle+'\n'+movieOverview);
        shareButton.setIntent(shareIntent);*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.details_share){
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            shareIntent.putExtra(Intent.EXTRA_TEXT,"Popcorn Movies: "+movieTitle+'\n'+movieOverview);
            item.setIntent(shareIntent);
            return super.onOptionsItemSelected(item);
        }
        if (item.getItemId() == android.R.id.home){
            getActivity().finish();
            return true;
        }
        return false;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
         rootView =  inflater.inflate(R.layout.fragment_detalis, container, false);

         intent = getActivity().getIntent();


            groupHeader = (ViewGroup) inflater.inflate(R.layout.details_header, container, false);


            int movieId = intent.getIntExtra("d6", 0);
            if (movieId != 0) {
                MovieInfo movie=new MovieInfo();
                movie.setTitle(intent.getStringExtra("d1"));
                getActivity().setTitle(intent.getStringExtra("d1"));
                movie.setImageURL(intent.getStringExtra("d2"));
                movie.setOverview(intent.getStringExtra("d5"));
                movie.setId(intent.getIntExtra("d6",0));
                movie.setReleaseDate(intent.getStringExtra("d3"));
                movie.setRating(Double.parseDouble(intent.getStringExtra("d4")));
                updateMovie(movie);
            }

        return rootView;
    }

    @Override
    public void updateMovie(MovieInfo movieInfo) {

        String movieTitle = movieInfo.getTitle();
        final String movieImageURL = movieInfo.getImageURL();
        String movieOverview = movieInfo.getOverview();
        String movieRating = movieInfo.getRating();
        String movieYear = movieInfo.getYear();
        final int movieId = movieInfo.getId();

        this.movieTitle = movieTitle;
        this.movieOverview = movieOverview;

        TextView title = (TextView) groupHeader.findViewById(R.id.details_title);
        ImageView image = (ImageView) groupHeader.findViewById(R.id.details_image);
        TextView year = (TextView) groupHeader.findViewById(R.id.details_year);
        RatingBar rating = (RatingBar) groupHeader.findViewById(R.id.details_rating);
        TextView description = (TextView) groupHeader.findViewById(R.id.details_description);

        title.setText(movieTitle);
        Picasso.with(getActivity()).load(movieImageURL).into(image);
        year.setText(movieYear);
        rating.setRating(Float.parseFloat(movieRating) / 2);
        description.setText(movieOverview);

        final String trailerUrl = "http://api.themoviedb.org/3/movie/" + movieId + "/videos?api_key=" + "5b5eb217409b64da538a976074c18c13";
        ArrayList<TrailerInfo> trailers = null;
        InfoLoader trailerLoader = new InfoLoader(trailerUrl, new TrailerParser());
        try {
            trailers = (ArrayList<TrailerInfo>) trailerLoader.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }



        Boolean fav = false;
        final Button favourite = (Button) groupHeader.findViewById(R.id.details_favourite);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Set<String> ids = sp.getStringSet("ids", new HashSet<String>());
        for (String id : ids) {
            if (Integer.parseInt(id) == movieId) {
                {
                    fav = true;
                }
            }
        }
        if (!fav) {
            favourite.setText("Not Favourite");
            //favourite.setBackgroundColor(Color.RED);
            favourite.setCompoundDrawablesWithIntrinsicBounds(R.drawable.fav0, 0, 0, 0);
        } else {
            favourite.setText("Favourite");
            //favourite.setBackgroundColor(Color.GREEN);
            favourite.setCompoundDrawablesWithIntrinsicBounds(R.drawable.fav1, 0, 0, 0);
        }

        String reviewUrl = "http://api.themoviedb.org/3/movie/" + movieId + "/reviews?api_key=" + "5b5eb217409b64da538a976074c18c13";
        final ArrayList<TrailerInfo> finalTrailers = trailers;
        InfoLoader reviewLoader = new InfoLoader(reviewUrl, new ReviewParser()) {
            @Override
            protected void onPostExecute(ArrayList<?> reviews) {
                super.onPostExecute(reviews);
                DetailsAdapter detailsAdapter = new DetailsAdapter(getActivity(), finalTrailers, (ArrayList<ReviewInfo>) reviews);
                ExpandableListView expandableListView = (ExpandableListView) rootView.findViewById(R.id.details_expandable);
                expandableListView.removeHeaderView(groupHeader);
                expandableListView.addHeaderView(groupHeader);
                expandableListView.setAdapter(detailsAdapter);
            }
        };
        reviewLoader.execute();

        final ArrayList<TrailerInfo> finalTrailers2 = trailers;
        ExpandableListView expandableListView = (ExpandableListView) rootView.findViewById(R.id.details_expandable);
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if (groupPosition==0) {

                    Intent trailerIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://youtu.be/" + finalTrailers2.get(childPosition).getKey()));
                    startActivity(trailerIntent);
                    return true;
                }
                return false;
            }
        });




        favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor editor = sp.edit();
                Set<String> ids = sp.getStringSet("ids", new HashSet<String>());
                Set<String> imageURLs = sp.getStringSet("imageURLs", new HashSet<String>());
                Boolean fav = false;
                for (String id : ids) {
                    if (Integer.parseInt(id) == movieId) {
                        {
                            fav = true;
                        }
                    }
                }
                if (!fav) {
                    ids.add(String.valueOf(movieId));
                    imageURLs.add(movieImageURL);
                    Toast.makeText(getActivity(), "Movie has been added to favourites", Toast.LENGTH_SHORT).show();
                    favourite.setText("Favourite");
                    //favourite.setBackgroundColor(Color.GREEN);
                    favourite.setCompoundDrawablesWithIntrinsicBounds(R.drawable.fav1, 0, 0, 0);
                } else {
                    ids.remove(String.valueOf(movieId));
                    imageURLs.remove(movieImageURL);
                    Toast.makeText(getActivity(), "This movie has been removed from favourites", Toast.LENGTH_SHORT).show();
                    favourite.setText("Not Favourite");
                    //favourite.setBackgroundColor(Color.RED);
                    favourite.setCompoundDrawablesWithIntrinsicBounds(R.drawable.fav0, 0, 0, 0);
                    MainFragment mainFragment = (MainFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.main_fragment_movies);
                    if(mainFragment != null){
                        mainFragment.onStart();
                    }
                }
                editor.putStringSet("ids", ids);
                editor.putStringSet("imageURLs", imageURLs);
                editor.commit();

            }
        });
    }
}