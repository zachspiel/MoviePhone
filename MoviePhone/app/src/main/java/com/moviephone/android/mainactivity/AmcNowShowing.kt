package com.moviephone.android.mainactivity

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.jsoup.Jsoup
import java.io.IOException
import java.util.*

class AmcNowShowing : AppCompatActivity() {
    private var recyclerView: RecyclerView? = null
    private var adapter: AmcParseAdapter? = null
    private val parseItems = ArrayList<ParseItem>()
    private var progressBar: ProgressBar? = null
    lateinit var mShare: Button

    override fun onCreate(SavedInstanceState: Bundle?) {
        super.onCreate(SavedInstanceState)
        setContentView(R.layout.activity_main)
        progressBar = findViewById(R.id.progressBar)
        recyclerView = findViewById(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView!!.setHasFixedSize(true)
        recyclerView!!.layoutManager = layoutManager
        adapter = AmcParseAdapter(parseItems, this)
        recyclerView!!.adapter = adapter
        val content =
            Content()
        content.execute()

    }

    private inner class Content :
        AsyncTask<Void?, Void?, Void?>() {
        override fun onPreExecute() {
            super.onPreExecute()
            progressBar!!.visibility = View.VISIBLE
        }

        override fun onPostExecute(aVoid: Void?) {
            super.onPostExecute(aVoid)
            progressBar!!.visibility = View.GONE
            adapter!!.notifyDataSetChanged()
        }

        override fun doInBackground(vararg params: Void?): Void? {
            try { // Url where API gets the information
                val url = "https://www.amctheatres.com/movies"
                val doc = Jsoup.connect(url).get()
                // Selects the box which contains the movie poster and movie title along with link for more information
                val data = doc.select("div.slide")
                val size = data.size
                for (i in 0 until size) {
                    val imgUrl =
                        data.select("div.slide").select("img").eq(i).attr("src")
                    val title = data.select("h3").eq(i).text()
                    val detailURL = data.select("div.slide > a:eq(0)").eq(i).attr("href")
                    parseItems.add(ParseItem(imgUrl, title, detailURL))
                    Log.d("Items", "img: $imgUrl . title: $title")
                }

            } catch (e: IOException) {
                e.printStackTrace()
            }
            return null
        }
    }
}