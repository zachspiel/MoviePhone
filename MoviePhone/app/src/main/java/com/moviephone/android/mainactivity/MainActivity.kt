package com.moviephone.android.mainactivity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var HarknsNowShowingBtn: Button? = null
    private var HarkinsComingSoonBtn: Button? = null
    private var AmcNowShowingBtn: Button? = null
    private var AmcComingSoonBtn: Button? = null
    override fun onCreate(SavedInstanceState: Bundle?) {

        super.onCreate(SavedInstanceState)
        setContentView(R.layout.main_menu)
        HarknsNowShowingBtn = findViewById(R.id.loadResultsButton)
        HarkinsComingSoonBtn = findViewById(R.id.loadMoviesComingSoon)
        AmcNowShowingBtn = findViewById(R.id.loadAMCMovies)
        AmcComingSoonBtn = findViewById(R.id.loadAMCMoviesComingSoon)

        with(HarknsNowShowingBtn) { this?.setOnClickListener { loadHarkingsNowShowing() } }
        with(HarkinsComingSoonBtn) { this?.setOnClickListener { loadHarkinsComingSoon() } }
        with(AmcNowShowingBtn) { this?.setOnClickListener { loadAmcNowShowing() } }
        with(AmcComingSoonBtn) { this?.setOnClickListener { loadAmcComingSoon() } }
    }

    /**
     * Methods for Harkins search
     */
    private fun loadHarkingsNowShowing() {
        val intent = Intent(this@MainActivity, HarkinsNowShowing::class.java)
        startActivity(intent)
    }

    private fun loadHarkinsComingSoon() {
        val intent = Intent(this@MainActivity, HarkinsComingSoon::class.java)
        startActivity(intent)
    }

    /**
     * Methods for AMC search
     */
    private fun loadAmcNowShowing() {
        val intent = Intent(this@MainActivity, AmcNowShowing::class.java)
        startActivity(intent)
    }

    private fun loadAmcComingSoon() {
        val intent = Intent(this@MainActivity, AmcComingSoon::class.java)
        startActivity(intent)
    }
}