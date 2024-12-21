package com.example.filmapiparcer.retrofit

import com.example.filmapiparcer.db.Movie
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface OMDbApi {
    @GET("/")
    suspend fun searchMovies(
        @Query("s") query: String,
        @Query("y") year: String? = null,
        @Query("apikey") apiKey: String
    ): Response<SearchResponse>
}

data class SearchResponse(
    val Search: List<Movie>,
    val totalResults: String,
    val Response: String
)

object RetrofitClient {
    private const val BASE_URL = "http://www.omdbapi.com"

    val api: OMDbApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OMDbApi::class.java)
    }
}