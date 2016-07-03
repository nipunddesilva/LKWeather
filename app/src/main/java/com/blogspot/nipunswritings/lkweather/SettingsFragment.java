package com.blogspot.nipunswritings.lkweather;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by Nipun on 7/3/2016.
 */
public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
    }
}
