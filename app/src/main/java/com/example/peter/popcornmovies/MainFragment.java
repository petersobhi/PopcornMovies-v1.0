package com.example.peter.popcornmovies;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.ExecutionException;


public class MainFragment extends Fragment{

    static final String TAG = "Main Fragment";
    View rootView;
    InfoLoader imageLoader;
    iObserverFragment observerFragment;
    GridView gridView;
    int gridViewPosition;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView= inflater.inflate(R.layout.fragment_main,container,false);
        setHasOptionsMenu(true);
        if(savedInstanceState != null && savedInstanceState.containsKey("GridViewPosition"))
             gridViewPosition = savedInstanceState.getInt("GridViewPosition");

        return rootView;
    }


    @Override
    public void onStart() {
        super.onStart();
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        final String sortBy = sharedPreferences.getString(getActivity().getString(R.string.pref_sortByKey), getActivity().getString(R.string.pref_sortByDefault));
        if(sortBy.equals("favourite")){
            HashSet<String> imagesSet = (HashSet<String>) sharedPreferences.getStringSet("imageURLs",new HashSet<String>());
            CustomAdapter customAdapter = new CustomAdapter(getActivity(), new ArrayList<String>(imagesSet));
            GridView gridView = (GridView) rootView.findViewById(R.id.gridView);
            gridView.setAdapter(customAdapter);
        }
        else {
            String mainUrl = "http://api.themoviedb.org/3/movie/" + sortBy + "?api_key=" + "5b5eb217409b64da538a976074c18c13";
            imageLoader = new InfoLoader(mainUrl, new ImageParser()) {
                protected void onPostExecute(ArrayList<?> images) {
                    super.onPostExecute(images);
                    CustomAdapter customAdapter = new CustomAdapter(getActivity(), (ArrayList<String>) images);
                    GridView gridView = (GridView) rootView.findViewById(R.id.gridView);
                    gridView.setAdapter(customAdapter);
                    gridView.setSelection(gridViewPosition);
                    if(! rootView.getTag().equals(getString(R.string.fragment_main_mobile))) {
                        gridView.setItemChecked(gridViewPosition,true);
                        Log.e(TAG, "onPostExecute: "+gridViewPosition);
                        try {
                                if(!sortBy.equals("favourite"))
                                {
                                    MovieInfo movie = MovieParser.parseAll(getJSONCode(),gridViewPosition);
                                    observerFragment.updateMovie(movie);
                                }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                    if (sortBy.equals("popular"))
                        sharedPreferences.edit().putString("JSON_P", this.getJSONCode()).commit();
                    if (sortBy.equals("top_rated"))
                        sharedPreferences.edit().putString("JSON_TR", this.getJSONCode()).commit();
                }

            };
            imageLoader.execute();
        }


        gridView = (GridView) rootView.findViewById(R.id.gridView);
        Log.e(TAG, "onStart: GridViewPosition = "+gridViewPosition);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                gridViewPosition = position;
                try {
                    MovieInfo movie;
                    String JSONCodeP = sharedPreferences.getString("JSON_P", null);
                    String JSONCodeTR = sharedPreferences.getString("JSON_TR", null);
                    if (sortBy.equals("favourite")) {
                        //ArrayList<String> movieIDs =  new ArrayList<String>(sharedPreferences.getStringSet("ids",new HashSet<String>()));
                        HashSet<String> imagesSet = (HashSet<String>) sharedPreferences.getStringSet("imageURLs", new HashSet<String>());
                        ArrayList<String> images = new ArrayList<>(imagesSet);
                        movie = MovieParser.parseAllFav(JSONCodeP, JSONCodeTR, images.get(position));
                    } else {
                        movie = MovieParser.parseAll(imageLoader.getJSONCode(), position);
                    }

                    if (rootView.getTag().equals(getString(R.string.fragment_main_mobile))) {
                        Intent intent = new Intent(getActivity(), DetailsActivity.class);
                        intent.putExtra("d1", movie.getTitle());
                        intent.putExtra("d2", movie.getImageURL());
                        intent.putExtra("d3", movie.getYear());
                        intent.putExtra("d4", movie.getRating());
                        intent.putExtra("d5", movie.getOverview());
                        intent.putExtra("d6", movie.getId());
                        startActivity(intent);
                    }
                    else {
                        observerFragment.updateMovie(movie);
                    }
                }catch(JSONException e){
                        e.printStackTrace();
                    }
            }
        });

    }


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.settings){
            Intent intent = new Intent(getActivity(),SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public void setObserverFragment(iObserverFragment observerFragment) {
        this.observerFragment = observerFragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("GridViewPosition",gridViewPosition);
    }
    
}
