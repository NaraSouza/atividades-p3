package br.ufpe.cin.if710.rss

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.preference.PreferenceFragmentCompat
import android.support.v7.widget.Toolbar
import kotlinx.android.synthetic.main.activity_preferences.*

class PreferencesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferences)
        setSupportActionBar(toolbar as Toolbar)

        supportFragmentManager.beginTransaction()
                .replace(R.id.content, PreferencesFragment())
                .commit()
    }

    class PreferencesFragment: PreferenceFragmentCompat() {

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.pref_settings)
        }
    }
}
