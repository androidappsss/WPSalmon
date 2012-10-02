package com.balitechy.wpsalmon;

import com.balitechy.wpsalmon.R;

import android.os.Bundle;
import android.preference.PreferenceActivity;

 
public class Settings extends PreferenceActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
        		//requestWindowFeature(Window.FEATURE_NO_TITLE);
                super.onCreate(savedInstanceState);
                addPreferencesFromResource(R.xml.preferences);
        }
}