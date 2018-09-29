package co.edu.aulamatriz.saveddataapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceFragment

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentManager.beginTransaction()
                .add(android.R.id.content, ConfiguracionFragment())
                .commit()

    }

    class ConfiguracionFragment: PreferenceFragment(){

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.preferences)
        }

    }
}
