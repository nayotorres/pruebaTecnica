package com.torres.pruebatecnica.domain

import com.torres.pruebatecnica.data.DataSource
import com.torres.pruebatecnica.data.model.MovieDao
import com.torres.pruebatecnica.data.model.MovieEntity
import com.torres.pruebatecnica.data.model.VideoEntity
import com.torres.pruebatecnica.vo.Resource

class DataRepositoryImp (private val remoteDataSource: DataSource):DataRepository{

    override suspend fun requestTopRated(): Resource<MovieEntity> {
        return remoteDataSource.requestTopRated()
    }

    override suspend fun requestUpcoming(): Resource<MovieEntity> {
        return remoteDataSource.requestUpcoming()
    }

    override suspend fun requestMovieById(id: Int): Resource<MovieEntity.Movie> {
        return remoteDataSource.requestMovieById(id)
    }

    override suspend fun requestVideoById(id: Int): Resource<VideoEntity> {
        return remoteDataSource.requestVideoById(id)
    }

    override suspend fun insertMovie(movieDao: MovieDao) {
        remoteDataSource.insertMovie(movieDao)
    }

    override suspend fun getMovie(): Resource<List<MovieDao>> {
        return remoteDataSource.getMovie()
    }
}