package com.torres.pruebatecnica.viewmodel

import androidx.lifecycle.*
import com.torres.pruebatecnica.domain.DataRepository
import com.torres.pruebatecnica.vo.Resource
import kotlinx.coroutines.Dispatchers

class DetailViewModel constructor(private val repository: DataRepository) : ViewModel(){

    private val _movieId = MutableLiveData<Int>()
    private val _videoId = MutableLiveData<Int>()

    fun setId(id:Int){
        _movieId.value = id
    }

    fun setVideoId(id:Int){
        _videoId.value = id
    }


    val movie = _movieId.distinctUntilChanged().switchMap {
        liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            emit(repository.requestMovieById(it))
        }
    }

    val video = _videoId.distinctUntilChanged().switchMap {
        liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            emit(repository.requestVideoById(it))
        }
    }
}