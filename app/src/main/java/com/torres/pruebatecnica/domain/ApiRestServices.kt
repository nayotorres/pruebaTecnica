package com.torres.pruebatecnica.domain

import com.torres.pruebatecnica.data.model.MovieEntity
import com.torres.pruebatecnica.data.model.VideoEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiRestServices {

    @Headers("Content-Type: application/json")
    @GET("movie/upcoming")
    suspend fun requestUpcoming(@Query("api_key") apikey: String): Response<MovieEntity>

    @Headers("Content-Type: application/json")
    @GET("movie/top_rated")
    suspend fun requestTopRated(@Query("api_key") apikey: String): Response<MovieEntity>

    @Headers("Content-Type: application/json")
    @GET("movie/{movie_id}")
    suspend fun requestMovie(@Path("movie_id") movieId:Int, @Query("api_key") apikey: String): Response<MovieEntity.Movie>

    @Headers("Content-Type: application/json")
    @GET("movie/{movie_id}/videos")
    suspend fun requestVideo(@Path("movie_id") movieId:Int, @Query("api_key") apikey: String): Response<VideoEntity>
}