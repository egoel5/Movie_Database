package com.example.project8

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import com.example.project8.model.OmdbSearchResult

public interface OmdbService {

    @GET("movies/search")
    fun searchMovies(
        @Header("Authorization") authHeader: String,
        @Query("term") searchTerm: String) : Call<OmdbSearchResult>
}