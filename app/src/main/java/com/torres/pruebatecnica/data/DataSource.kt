package com.torres.pruebatecnica.data

import com.torres.pruebatecnica.data.model.MovieDao
import com.torres.pruebatecnica.data.model.MovieEntity
import com.torres.pruebatecnica.data.model.VideoEntity
import com.torres.pruebatecnica.vo.Resource

interface DataSource {

    suspend fun requestTopRated():Resource<MovieEntity>

    suspend fun requestUpcoming():Resource<MovieEntity>

    suspend fun requestMovieById(id:Int):Resource<MovieEntity.Movie>

    suspend fun requestVideoById(id:Int):Resource<VideoEntity>

    suspend fun insertMovie(movieDao: MovieDao)

    suspend fun getMovie():Resource<List<MovieDao>>
}