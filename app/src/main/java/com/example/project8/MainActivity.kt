package com.example.project8

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.project8.model.OmdbSearchResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://www.omdbapi.com"
private const val API_KEY = "ec89d91"
class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit =
            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build()

        val omdbService = retrofit.create(OmdbService::class.java)
        omdbService.searchMovies("Bearer $API_KEY", "Inception").enqueue(object :
        Callback<OmdbSearchResult> {
            override fun onResponse(
                call: Call<OmdbSearchResult>,
                response: Response<OmdbSearchResult>
            ) {
                val body = response.body()
                if (body == null) {
                    Log.w(TAG, "Did not receive valid response body from Yelp API... exiting")
                    return
                }

            }

            override fun onFailure(call: Call<OmdbSearchResult>, t: Throwable) {
                Log.i(TAG, "onFailure $t")
            }

        })
    }
}