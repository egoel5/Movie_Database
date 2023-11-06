package com.example.project8

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"

    // set up lateinits for every view on MainActivity
    lateinit var ombdApiClient : OmdbService
    lateinit var tvTitle : TextView
    lateinit var tvRating : TextView
    lateinit var tvGenre : TextView
    lateinit var tvIMDB : TextView
    lateinit var tvRelease : TextView
    lateinit var tvRuntime : TextView
    lateinit var tvImdbLink : TextView
    lateinit var butSearch : Button
    lateinit var etTitle : EditText
    lateinit var imgPoster : ImageView
    lateinit var butShare : ImageButton
    lateinit var butFeedback : ImageButton
    lateinit var sendIntent: Intent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // initialize all views
        ombdApiClient = OMDbApiClient.getOMDbApi()!!
        tvTitle = findViewById(R.id.tvTitle)
        tvRating = findViewById(R.id.tvRating)
        tvGenre = findViewById(R.id.tvGenre)
        tvIMDB = findViewById(R.id.tvIMDB)
        tvRelease = findViewById(R.id.tvRelease)
        tvRuntime = findViewById(R.id.tvRuntime)
        tvImdbLink = findViewById(R.id.tvImdbLink)
        butSearch = findViewById(R.id.butSearch)
        etTitle = findViewById(R.id.etTitle)
        imgPoster = findViewById(R.id.imageView)
        butShare = findViewById(R.id.imgButShare)
        butFeedback = findViewById(R.id.imgButFeedback)

        /**
         * When search button is clicked:
         * - set searchTitle to etTitle.text
         * - searchMovies using searchTitle from ombdApiClient
         * - onResponse:
         *      - make tvRating and butShare visible, then call displayData to fill all values
         *      - set the sendIntent for share button
         * - onFailure:
         *      - Log failure
         */
        butSearch.setOnClickListener {
            val searchTitle = etTitle.text.toString()
            ombdApiClient.searchMovies(searchTitle).enqueue(object : Callback<MovieList> {
                override fun onResponse(
                    call: Call<MovieList>,
                    response: Response<MovieList>
                ) {
                    val body = response.body()
                    if (body == null) {
                        Log.w(TAG, "Did not receive valid response body from Yelp API... exiting")
                        return
                    }
                    tvRating.visibility = View.VISIBLE
                    butShare.visibility = View.VISIBLE
                    displayData(response.body()!!)
                    sendIntent= Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, "${response.body()!!.Title}\n\nhttps://www.imdb.com/title/${response.body()!!.imdbID}/")
                        type = "text/plain"
                    }
                }

                override fun onFailure(call: Call<MovieList>, t: Throwable) {
                    Log.i(TAG, "onFailure $t")
                }
            })
        }

        /**
         * when IMDB Link is clicked, set url and intent to go to url and then startActivity and go to IMDB page
         */
        tvImdbLink.setOnClickListener {
            val url: String = tvImdbLink.text.toString()
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }

        /**
         * when share button is clicked, set intent to shareIntent and then startActivity to display share screen
         */
        butShare.setOnClickListener {
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

        /**
         * when feedback button is clicked on toolbar, set email recipient, subject and body
         * then build an email using the above subject and body, then set mailIntent and startActivity
         * sending the mail
         */
        butFeedback.setOnClickListener{
            val to = "egoel13@gmail.com"
            val subject = "User Feedback for Movie Database"
            val body = "My feedback: "

            val uriBuilder = StringBuilder("mailto:" + Uri.encode(to))
            uriBuilder.append("?subject=" + Uri.encode(subject))
            uriBuilder.append("&body=" + Uri.encode(body))
            val uriString = uriBuilder.toString()

            val mailIntent = Intent(Intent.ACTION_SENDTO, Uri.parse(uriString))
            startActivity(mailIntent)
        }
    }

    /**
     * displayData(MovieList)
     *
     * set tvTitle text to movie's Title value
     * set tvRating text to movie's Rated value
     * set tvGenre text to movie's Genre value
     * set tvIMDB rating to movie's imdbRating value out of 10
     * set tvRelease to movie's Year value
     * set tvRuntime to movie's Runtime value
     * set tvImdbLink text to default imdb url + movie's imdb id
     * set imgPoster image to movie's poster using Glide
     */
    private fun displayData(data: MovieList) {
        tvTitle.text = data.Title
        tvRating.text = data.Rated
        tvGenre.text = data.Genre
        tvIMDB.text = "${data.imdbRating} / 10"
        tvRelease.text = data.Year
        tvRuntime.text = data.Runtime
        tvImdbLink.text = "https://www.imdb.com/title/${data.imdbID}/"
        Glide.with(this).load(data.Poster).into(imgPoster)
    }
}