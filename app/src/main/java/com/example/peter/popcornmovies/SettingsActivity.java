package com.example.peter.popcornmovies;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.widget.Toolbar;

/**
 * Created by Peter on 22/03/16.
 */
public class SettingsActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_general);
        ListPreference sortBy = (ListPreference) findPreference(getString(R.string.pref_sortByKey));
        sortBy.setOnPreferenceChangeListener(this);
        sortBy.setSummary(sortBy.getEntry());
    }


    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        ListPreference listPreference = (ListPreference) preference;
        int prefIndex = listPreference.findIndexOfValue(newValue.toString());
            preference.setSummary(listPreference.getEntries()[prefIndex]);
        return true;
    }

}
