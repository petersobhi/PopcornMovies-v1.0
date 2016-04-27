package com.example.peter.popcornmovies;

import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getSupportFragmentManager();
        MainFragment mainFragment = (MainFragment) fragmentManager.findFragmentById(R.id.main_fragment_movies);
        DetalisFragment detalisFragment = (DetalisFragment) fragmentManager.findFragmentById(R.id.main_fragment_details);
        mainFragment.setObserverFragment(detalisFragment);
    }
}
