package com.example.project8

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
class OMDbApiClient {
    companion object{
        private var omdbapi: OmdbService? = null

        fun getOMDbApi(): OmdbService?{
            if(omdbapi == null){
                omdbapi = Retrofit.Builder()
                    .baseUrl("https://www.omdbapi.com/?&apikey=ec89d91")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(OmdbService::class.java);
            }

            return omdbapi
        }

    }
}