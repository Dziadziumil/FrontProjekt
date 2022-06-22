package com.example.gamerfinder.fragments.lobbies

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gamerfinder.utils.*

class LobbiesViewModel(application: Application) : AndroidViewModel(application) {

    private val _lobbies = MutableLiveData<List<Lobby>>()
    val lobbies: LiveData<List<Lobby>> = _lobbies

    fun getLobbies(gameId: Int, context: Context) {
        HttpGet.GetLobbies.apply {
            this.addListener(HttpListener(object : Action<ResponseModels.LoobiesList> {
                override fun onMessage(isSuccess: Boolean, value: ResponseModels.LoobiesList?) {
                    if(isSuccess) {
                        value?.let {
                            _lobbies.postValue(value.lobbies!!)
                        }
                    } else {
                        println("couldn't get lobbies")
                    }
                }
            }))
        }.requestGet(context, gameId.toString())
    }

    fun getMyLobbies(context: Context) {
        HttpGet.GetMyLobbies.apply {
            this.addListener(HttpListener(object : Action<ResponseModels.LoobiesList> {
                override fun onMessage(isSuccess: Boolean, value: ResponseModels.LoobiesList?) {
                    if(isSuccess) {
                        value?.let {
                            _lobbies.postValue(value.lobbies!!)
                        }
                    } else {
                        println("couldn't get lobbies")
                    }
                }
            }))
        }.requestGet(context, AccountService(context).getCurrentUserId())
    }
}