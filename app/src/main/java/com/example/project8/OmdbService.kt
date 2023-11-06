package com.example.project8

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbService {

    @GET("?&apikey=ec89d91")
    fun searchMovies(
        @Query("t") title: String): Call<MovieList>
}