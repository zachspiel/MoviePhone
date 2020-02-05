package com.moviephone.android.mainactivity

import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import org.jsoup.Jsoup
import java.io.IOException

class AmcDetailActivity : AppCompatActivity() {
    private var imageView: ImageView? = null
    private var titleTextView: TextView? = null
    private var detailTextView: TextView? = null
    private var synopsisTextView: TextView? = null
    private var ageRatingTextView: TextView? = null
    private var detailString: String? = null
    private var synopsis: String? = null
    private var ageRating: String? = null
    private var movieURL: String? = null
    private var movieLink: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        imageView = findViewById(R.id.imageView)
        titleTextView = findViewById(R.id.textView)
        detailTextView = findViewById(R.id.detailTextView)
        synopsisTextView = findViewById(R.id.synopsis)
        ageRatingTextView = findViewById(R.id.rating)
        movieLink = findViewById(R.id.movieLink)

        titleTextView!!.text = intent.getStringExtra("title")

        Picasso.get().load(intent.getStringExtra("image")).into(imageView)
        val content =
            Content()
        content.execute()
        movieLink!!.setOnClickListener { loadWebsite() }
    }

    private fun loadWebsite() {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.addCategory(Intent.CATEGORY_BROWSABLE)
        intent.data = Uri.parse(movieURL)
        startActivity(intent)
    }

    private inner class Content :
        AsyncTask<Void?, Void?, Void?>() {

        override fun onPostExecute(aVoid: Void?) {
            super.onPostExecute(aVoid)
            detailTextView!!.text = detailString
            synopsisTextView!!.text = synopsis
            ageRatingTextView!!.text = ageRating
        }

        override fun doInBackground(vararg params: Void?): Void? {
            try {
                val baseUrl = "https://www.amctheatres.com"
                val detailUrl = intent.getStringExtra("detailUrl")

                val url = baseUrl + detailUrl
                movieURL = url
                val doc = Jsoup.connect(url).get()
                val data = doc.select("div.row")
                val synopsisDiv = doc.select("div.Intro-text")
                Log.d("Items", "URL$url")
                detailString =
                    "Starring: " + data.select("div.MovieCastCrewList-right > h3 > span:eq(0)").text()
                synopsis =
                    "Synopsis: " + synopsisDiv.text()
                ageRating = "Rating: " + data.select(" li.Headline--eyebrow > span:eq(1)").text()

            } catch (e: IOException) {
                e.printStackTrace()
            }
            return null
        }
    }
}