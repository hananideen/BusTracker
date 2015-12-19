package com.tracking.bus;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by Hananideen on 20/12/2015.
 */
public class SettingsActivity extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);

    }
}
