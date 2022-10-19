package com.torres.pruebatecnica.domain

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.torres.pruebatecnica.data.model.MovieDao

@Dao
interface DataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieDao)

    @Query("SELECT * FROM movieEntity")
    suspend fun getMovies(): List<MovieDao>
}