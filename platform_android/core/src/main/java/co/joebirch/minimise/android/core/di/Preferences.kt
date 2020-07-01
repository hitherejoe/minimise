package co.joebirch.minimise.android.core.di

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class Preferences @Inject constructor(
    @ApplicationContext val context: Context
) {

    private var sharedPref: SharedPreferences = context.getSharedPreferences("minimise_prefs", 0)

    var accessToken: String?
        get() = sharedPref.getString(KEY_AUTH_TOKEN, null)
        set(value) {
            sharedPref.edit().putString(KEY_AUTH_TOKEN, value).apply()
        }

    companion object {
        const val KEY_AUTH_TOKEN = "KEY_AUTH_TOKEN"
    }
}