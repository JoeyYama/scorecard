package com.jyamanouchi.golfscorebasic;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.PreferenceFragmentCompat;

public class PlayersFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    EditTextPreference etp1, etp2, etp3, etp4;
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.players);

        etp1 = (EditTextPreference)findPreference("player1");
        if(etp1.getText() != null) {
            etp1.setTitle(etp1.getText().toUpperCase());
        }
        etp2 = (EditTextPreference)findPreference("player2");
        if(etp2.getText() != null) {
            etp2.setTitle(etp2.getText().toUpperCase());
        }
        etp3 = (EditTextPreference)findPreference("player3");
        if(etp3.getText() != null) {
            etp3.setTitle(etp3.getText().toUpperCase());
        }
        etp4 = (EditTextPreference)findPreference("player4");
        if(etp4.getText() != null) {
            etp4.setTitle(etp4.getText().toUpperCase());
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        switch(key) {
          case "player1":
            etp1 = (EditTextPreference)findPreference(key);
            etp1.setTitle(etp1.getText().toUpperCase());
            break;
          case "player2":
            etp2 = (EditTextPreference)findPreference(key);
            etp2.setTitle(etp2.getText().toUpperCase());
            break;
          case "player3":
            etp3 = (EditTextPreference)findPreference(key);
            etp3.setTitle(etp3.getText().toUpperCase());
            break;
          case "player4":
            etp4 = (EditTextPreference)findPreference(key);
            etp4.setTitle(etp4.getText().toUpperCase());
            break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }
}
