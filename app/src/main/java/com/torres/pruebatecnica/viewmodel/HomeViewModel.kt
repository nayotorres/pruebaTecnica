package com.torres.pruebatecnica.viewmodel

import androidx.lifecycle.*
import com.torres.pruebatecnica.data.model.MovieDao
import com.torres.pruebatecnica.domain.DataRepository
import com.torres.pruebatecnica.vo.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel constructor(private val repository: DataRepository) : ViewModel() {

    private val _upcoming = MutableLiveData<String>()
    private val _topRated = MutableLiveData<String>()
    private val _movieDB = MutableLiveData<String>()

    fun setUpcoming(value:String){
        _upcoming.value = value
    }

    fun setTopRated(value:String){
        _topRated.value = value
    }


    fun setMovieBD(item:String){
        _movieDB.value = item
    }

    val upcoming = _upcoming.distinctUntilChanged().switchMap {
        liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            emit(repository.requestUpcoming())
        }
    }

    val topRated = _topRated.distinctUntilChanged().switchMap {
        liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            emit(repository.requestTopRated())
        }
    }

    fun saveMovie(movieDao: MovieDao){
        viewModelScope.launch {
            repository.insertMovie(movieDao)
        }
    }

    val movieDB = _movieDB.distinctUntilChanged().switchMap {
        liveData(Dispatchers.IO) {
            emit(repository.getMovie())
        }
    }


}