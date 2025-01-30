package com.example.t3a3_benavent_vicent3.activities

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.practicaroom.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }
}
