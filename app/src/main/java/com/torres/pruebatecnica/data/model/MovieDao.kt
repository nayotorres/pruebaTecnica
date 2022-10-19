package com.torres.pruebatecnica.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movieEntity")
data class MovieDao(
    @PrimaryKey
    val uuidMovie:Int,
    @ColumnInfo(name =  "name_movie")
    val nameMovie:String="",
    @ColumnInfo(name =  "overview")
    val overview:String="",
    @ColumnInfo(name =  "poster_path")
    val posterPath:String="",
    @ColumnInfo(name =  "release_date")
    val releaseDate:String="",
    @ColumnInfo(name =  "original_language")
    val originalLanguage:String="",
    @ColumnInfo(name =  "category")
    val category:Int=0)