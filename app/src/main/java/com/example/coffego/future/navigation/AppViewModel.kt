package com.example.coffego.future.navigation

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    sharedPreferences: SharedPreferences
) : ViewModel() {

    init {
        viewModelScope.launch {
            sharedPreferences.registerOnSharedPreferenceChangeListener { sharedPreferences, key ->
                run {
                    if (key == "TOKEN") {
                        Log.e("PL:L", "SparkPost")
                    }
                }
            }
            }
        }

}