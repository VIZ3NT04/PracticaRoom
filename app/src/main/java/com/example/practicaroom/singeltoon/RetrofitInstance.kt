package com.example.practicaroom.singeltoon

import com.example.practicaroom.service.PorkemonService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL="https://pokeapi.co/api/v2/pokemon/"
    val api: PorkemonService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PorkemonService::class.java)
    }
}