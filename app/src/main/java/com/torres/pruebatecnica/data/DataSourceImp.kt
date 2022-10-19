package com.torres.pruebatecnica.data

import com.torres.pruebatecnica.aplication.App
import com.torres.pruebatecnica.data.model.MovieDao
import com.torres.pruebatecnica.data.model.MovieEntity
import com.torres.pruebatecnica.data.model.VideoEntity
import com.torres.pruebatecnica.domain.ApiRestServices
import com.torres.pruebatecnica.util.Constants
import com.torres.pruebatecnica.util.Utils
import com.torres.pruebatecnica.vo.ApiClient
import com.torres.pruebatecnica.vo.AppDatabase
import com.torres.pruebatecnica.vo.Resource
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import java.io.IOException

class DataSourceImp(private val appDatabase: AppDatabase):DataSource {

    override suspend fun requestTopRated(): Resource<MovieEntity> {
        val service = ApiClient.createService(ApiRestServices::class.java)
        return when (val response = processCall { service.requestTopRated(Constants.API_KEY) }) {
            is MovieEntity -> {
                Resource.Success(data = response as MovieEntity)
            }
            else -> {
                Resource.DataError(errorMessage = response as String)
            }
        }
    }

    override suspend fun requestUpcoming(): Resource<MovieEntity> {
        val service = ApiClient.createService(ApiRestServices::class.java)
        return when (val response = processCall { service.requestUpcoming(Constants.API_KEY) }) {
            is MovieEntity -> {
                Resource.Success(data = response as MovieEntity)
            }
            else -> {
                Resource.DataError(errorMessage = response as String)
            }
        }
    }

    override suspend fun requestMovieById(id: Int): Resource<MovieEntity.Movie> {
        val service = ApiClient.createService(ApiRestServices::class.java)
        return when (val response = processCall { service.requestMovie(id,Constants.API_KEY) }) {
            is MovieEntity.Movie -> {
                Resource.Success(data = response as MovieEntity.Movie)
            }
            else -> {
                Resource.DataError(errorMessage = response as String)
            }
        }
    }

    override suspend fun requestVideoById(id: Int): Resource<VideoEntity> {
        val service = ApiClient.createService(ApiRestServices::class.java)
        return when (val response = processCall { service.requestVideo(id,Constants.API_KEY) }) {
            is VideoEntity -> {
                Resource.Success(data = response as VideoEntity)
            }
            else -> {
                Resource.DataError(errorMessage = response as String)
            }
        }
    }

    override suspend fun insertMovie(movieDao: MovieDao) {
        appDatabase.accessDao().insertMovie(movieDao)
    }

    override suspend fun getMovie(): Resource<List<MovieDao>> {
        return Resource.Success(data = appDatabase.accessDao().getMovies())
    }


    private suspend fun processCall(responseCall: suspend () -> Response<*>): Any? {
        if (!Utils.isConnect(App.appInstance!!.applicationContext)) {
            return "Sin acceso a internet"
        } else {
            return try {
                val response = responseCall.invoke()
                if (response.isSuccessful) {
                    response.body()
                } else {
                    val message = try {
                        val jsonObject = JSONObject(response!!.errorBody()!!.string())
                        jsonObject.getString("message")
                    } catch (e: JSONException) {
                        e.message
                    } catch (e: IOException) {
                        e.message
                    }
                    message
                }
            } catch (e: IOException) {
                e.message
            }
        }
    }
}