package com.example.project8.model

import com.google.gson.annotations.SerializedName

data class OmdbLocation(
    @SerializedName("address1") val address: String
)
