package com.example.manoj.newsly;

import android.content.SharedPreferences;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

    }


    //creating a prefrence fragment for the settings
    public static class NewsPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            //connecting with the setting xml
            //where the order and magnitude will be selected
            addPreferencesFromResource(R.xml.setting_main);

            //getting the value of the magnitude and displaying it
            Preference newsSource = findPreference(getString(R.string.news_source_key));
            bindPreferenceSummaryToValue(newsSource);

            //getting the sortby status and displaying it
            Preference sortBy = findPreference(getString(R.string.news_sortby_key));
            bindPreferenceSummaryToValue(sortBy);

        }

        //if there is a change in the preference then this method will be calles.
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();
            if (preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                int prefIndex = listPreference.findIndexOfValue(stringValue);
                if (prefIndex >= 0) {
                    CharSequence[] labels = listPreference.getEntries();
                    preference.setSummary(labels[prefIndex]);
                }
            } else {
                preference.setSummary(stringValue);
            }
            return true;
        }

        //this method will be called for binding the values.
        private void bindPreferenceSummaryToValue(Preference preference) {
            preference.setOnPreferenceChangeListener(this);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            String preferenceString = preferences.getString(preference.getKey(), "");
            //if the prefernece are change than that method will be calles.
            onPreferenceChange(preference, preferenceString);
        }
    }
}
