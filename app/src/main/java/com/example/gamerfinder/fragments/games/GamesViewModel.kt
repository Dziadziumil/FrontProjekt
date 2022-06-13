package com.example.gamerfinder.fragments.games

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gamerfinder.utils.*

enum class GetGamesStatus { LOADING, ERROR, SUCCESS }

class GamesViewModel(application: Application) : AndroidViewModel(application) {

    init {
        getGames(application.baseContext)
    }

    private val _games = MutableLiveData<List<Game>>()
    val games: LiveData<List<Game>> = _games

    private val _getGamesStatus = MutableLiveData<GetGamesStatus>()
    val getGamesStatus: LiveData<GetGamesStatus> = _getGamesStatus

    private fun getGames(context: Context) {
        HttpGet.GetGames.apply {
            this.addListener(HttpListener(object : Action<ResponseModels.GamesList> {
                override fun onMessage(isSuccess: Boolean, value: ResponseModels.GamesList?) {
                    if(isSuccess) {
                        value?.let {
                            _games.postValue(it.games!!)
                        }
                    } else {
                        println("couldn't get games")
                    }
                }
            }))
        }.requestGet(context)
    }
}