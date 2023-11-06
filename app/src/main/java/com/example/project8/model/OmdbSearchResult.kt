package com.example.project8.model

import com.google.gson.annotations.SerializedName

data class OmdbSearchResult(
    @SerializedName("total") val total: Int,
    @SerializedName("movies") val movies: List<OmdbMovie>
)
