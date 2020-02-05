package com.moviephone.android.mainactivity

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.jsoup.Jsoup
import java.io.IOException
import java.util.*

class HarkinsComingSoon : AppCompatActivity() {
    private var recyclerView: RecyclerView? = null
    private var adapter: HarkinsParseAdapter? = null
    private val parseItems = ArrayList<ParseItem>()
    private var progressBar: ProgressBar? = null
    override fun onCreate(SavedInstanceState: Bundle?) {
        super.onCreate(SavedInstanceState)
        setContentView(R.layout.activity_main)
        progressBar = findViewById(R.id.progressBar)
        recyclerView = findViewById(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView!!.setHasFixedSize(true)
        recyclerView!!.layoutManager = layoutManager
        adapter = HarkinsParseAdapter(parseItems, this)
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
                val url = "https://www.harkins.com/movies/coming-soon"
                val doc = Jsoup.connect(url).get()
                // Selects the box which contains the movie poster and movie title along with link for more information
                val data = doc.select("li.posters-container")
                val size = data.size
                for (i in 0 until size) {
                    val imgUrl =
                        data.select("li.posters-container").select("img").eq(i).attr("src")
                    val title = data.select("h2").eq(i).text()
                    val detailURL =
                        data.select("li.posters-container").select("a").eq(i).attr("href")
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