package br.ufpe.cin.if710.rss

import android.support.v7.app.AppCompatActivity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    /*companion object {
        private const val RSS_FEED = "http://leopoldomt.com/if1001/g1brasil.xml"
    }*/
    val rssfeed = "rssfeed"
    var prefs : SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = prefs!!.edit()
        editor.putString(rssfeed, getString(R.string.rssfeed))
        editor.apply()
    }

    override fun onStart() {
        super.onStart()

        var list : List<ItemRSS>
        //carregamento assincrono do xml
        doAsync {
            val feedXML = getRSSFeed(prefs!!.getString(rssfeed, ""))
            list = ParserRSS.parse(feedXML)
            Log.d("List", list.toString())

            uiThread {
                conteudoRSS.apply {
                    layoutManager = LinearLayoutManager(applicationContext)

                    //Definindo adapter
                    adapter = ItemAdapter(list, applicationContext)
                    Log.d("TESTE", "setou o adapter")

                    //ItemDecoration permite adicionar dividers
                    //Só é suportado a partir de targetSDKversion 22+
                    addItemDecoration(DividerItemDecoration(applicationContext, LinearLayoutManager.VERTICAL))
                }
            }
        }
    }

    //download no xml
    @Throws(IOException::class)
    private fun getRSSFeed(feed : String) : String {
        var rssFeed = ""
        val url = URL(feed)
        val conn = url.openConnection() as HttpURLConnection
        try {
            rssFeed = conn.inputStream.bufferedReader().readText()
        } finally {
            conn.disconnect()
        }
        Log.d("RSS_FEED", rssFeed)
        return rssFeed
    }

    /*override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_editpreferences -> {
            startActivity(Intent(applicationContext, PreferencesActivity::class.java))
            true
        }
        else -> super.onOptionsItemSelected(item)
    }*/
}
