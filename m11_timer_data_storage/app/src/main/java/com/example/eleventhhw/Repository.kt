package com.example.eleventhhw

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

private const val SHARED_PREFERENCE_NAME = "SharedPreference"
private const val SHARED_PREFERENCE_KEY = "SharedPreferenceKey"

class Repository {
    private lateinit var sharedPreference: SharedPreferences
    private var localVariable: String? = null

    private fun getDataFromLocalVariable(): String? {
        return localVariable
    }

    private fun getDataFromSharedPreference(): String? {
        return sharedPreference.getString(SHARED_PREFERENCE_KEY, "")
    }

    private fun saveDataFromSharedPreference(text: String) {
        sharedPreference.edit()
            .putString(SHARED_PREFERENCE_KEY, text)
            .apply()
    }

    @SuppressLint("CommitPrefEdits")
    private fun clearDataFromSharedPreference() {
        sharedPreference.edit()
            .clear()
            .apply()
    }

    fun saveText(text: String) {
        saveDataFromSharedPreference(text)
        localVariable = text
    }

    fun clearText() {
        clearDataFromSharedPreference()
        localVariable = null
    }

    fun getText(context: Context): String {
        sharedPreference =
            context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
        return when {
            getDataFromLocalVariable() != null -> getDataFromLocalVariable()!!
            getDataFromSharedPreference() != null -> getDataFromSharedPreference()!!
            else -> ""
        }
    }
}
