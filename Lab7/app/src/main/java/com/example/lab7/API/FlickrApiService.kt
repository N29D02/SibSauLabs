package com.example.lab7.API

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApiService {
    @GET("services/rest/")
    suspend fun getRecentPhotos(
        @Query("method") method: String = "flickr.photos.getRecent",
        @Query("api_key") apiKey: String = "dbe2234de5e71758f77eda26b415fe41",
        @Query("format") format: String = "json",
        @Query("nojsoncallback") noJsonCallback: Int = 1
    ): Response<FlickrResponse>

    @GET("services/rest/")
    suspend fun searchPhotos(
        @Query("method") method: String = "flickr.photos.search",
        @Query("api_key") apiKey: String = "dbe2234de5e71758f77eda26b415fe41",
        @Query("text") query: String,
        @Query("format") format: String = "json",
        @Query("nojsoncallback") noJsonCallback: Int = 1
    ): Response<FlickrResponse>
}

data class FlickrResponse(
    val photos: Photos
)

data class Photos(
    val photo: List<Photo>
)

data class Photo(
    val id: String,
    val owner: String,
    val secret: String,
    val server: String,
    val farm: Int,
    val title: String
)