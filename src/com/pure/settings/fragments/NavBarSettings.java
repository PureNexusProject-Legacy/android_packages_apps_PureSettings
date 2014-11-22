/*
 * Copyright (C) 2017 The Pure Nexus Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pure.settings.fragments;

import android.content.ContentResolver;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.preference.PreferenceScreen;
import android.support.v7.preference.Preference;
import android.support.v7.preference.Preference.OnPreferenceChangeListener;

import com.android.internal.logging.MetricsProto.MetricsEvent;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

import com.pure.settings.preferences.CustomSeekBarPreference;

public class NavBarSettings extends SettingsPreferenceFragment
         implements OnPreferenceChangeListener {

    private static final String LONG_PRESS_KILL_DELAY = "long_press_kill_delay";

    private CustomSeekBarPreference mLongpressKillDelay;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.navbar_settings);
        PreferenceScreen prefScreen = getPreferenceScreen();
        ContentResolver resolver = getActivity().getContentResolver();

        mLongpressKillDelay = (CustomSeekBarPreference) findPreference(LONG_PRESS_KILL_DELAY);
        int killconf = Settings.System.getInt(resolver,
                Settings.System.LONG_PRESS_KILL_DELAY, 1000);
        mLongpressKillDelay.setValue(killconf);
        mLongpressKillDelay.setOnPreferenceChangeListener(this);
    }

    @Override
    protected int getMetricsCategory() {
        return MetricsEvent.PURE;
    }

     @Override
     public boolean onPreferenceChange(Preference preference, Object newValue) {
        ContentResolver resolver = getActivity().getContentResolver();
        if (preference == mLongpressKillDelay) {
            int killconf = (Integer) newValue;
            Settings.System.putInt(resolver, Settings.System.LONG_PRESS_KILL_DELAY, killconf);
            return true;
        }
        return false;
    }
}

