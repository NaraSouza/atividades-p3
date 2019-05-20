package br.ufpe.cin.if710.rss

import android.app.Activity
import android.os.Bundle
import android.provider.SyncStateContract
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.Charset

class MainActivity : Activity() {

    companion object {
        private const val RSS_FEED = "http://leopoldomt.com/if1001/g1brasil.xml"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()

        var list : List<ItemRSS> = emptyList()
        doAsync {
            val feedXML = getRSSFeed(RSS_FEED)
            list = ParserRSS.parse(feedXML)
            Log.d("List", list.toString())

            uiThread {
                conteudoRSS.apply {
                    layoutManager = LinearLayoutManager(applicationContext)

                    //Definindo o adapter - aqui não tem muita diferença de ListView
                    adapter = ItemAdapter(list, applicationContext)
                    Log.d("TESTE", "setou o adapter")

                    //ItemDecoration permite adicionar dividers
                    //Só é suportado a partir de targetSDKversion 22+
                    addItemDecoration(DividerItemDecoration(applicationContext, LinearLayoutManager.VERTICAL))
                }
            }
        }
    }

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
}
