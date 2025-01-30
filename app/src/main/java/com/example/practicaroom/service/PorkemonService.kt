package com.example.practicaroom.service

import android.telecom.Call
import androidx.room.Query
import com.example.practicaroom.`object`.Pokemon

import retrofit2.http.GET
import retrofit2.http.Path

interface PorkemonService {
    @GET("{name}")
    suspend fun getPokemons(@Path("name") name: String): Pokemon
}