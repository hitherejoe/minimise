package co.joebirch.minimise.authentication

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity: AppCompatActivity() {

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, SettingsActivity::class.java)
        }
    }


}