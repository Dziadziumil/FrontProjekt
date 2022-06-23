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

    private val _lobby = MutableLiveData<ResponseModels.LobbyResponse>()
    val lobby: LiveData<ResponseModels.LobbyResponse> = _lobby

    private val _usersInLobbies = MutableLiveData<List<ResponseModels.UsersInLobbies>>()
    val usersInLobbies: LiveData<List<ResponseModels.UsersInLobbies>> = _usersInLobbies

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

    fun getLobby(lobbyId: Int, context: Context) {
        HttpGet.GetLobby.apply {
            this.addListener(HttpListener(object : Action<ResponseModels.LobbyResponse> {
                override fun onMessage(isSuccess: Boolean, value: ResponseModels.LobbyResponse?) {
                    if(isSuccess) {
                        _lobby.postValue(value!!)
                    } else {
                        println("couldn't get lobby")
                    }
                }
            }))
        }.requestGet(context, lobbyId.toString())
    }

    fun joinLobby(lobbyId: Int, context: Context) {
        HttpPost.JoinLobby.apply {
            this.addListener(HttpListener(object : Action<ResponseModels.LobbyResponse> {
                override fun onMessage(isSuccess: Boolean, value: ResponseModels.LobbyResponse?) {
                    if(isSuccess) {
                        _lobby.postValue(value!!)
                    } else {
                        println("couldn't get lobby")
                    }
                }
            }))
        }.requestPost(
            RequestModels.JoinLobbyRequest(
            userId = AccountService(context).getCurrentUserId().toInt(),
            lobbyId = lobbyId
            ),
            context
        )
    }

    fun getUsersInLobbies(context: Context) {
        HttpGet.GetUsersInLobbies.apply {
            this.addListener(HttpListener(object : Action<List<ResponseModels.UsersInLobbies>> {
                override fun onMessage(
                    isSuccess: Boolean,
                    value: List<ResponseModels.UsersInLobbies>?
                ) {
                    if(isSuccess) {
                        _usersInLobbies.postValue(value!!)
                    } else {
                        println("couldn't get users in lobbies")
                    }
                }
            }))
        }.requestGet(context)
    }

    fun isInLobby(lobbyId: Int, context: Context): Boolean {
        usersInLobbies.value?.forEach {
            if(it.lobbyId == lobbyId) {
                if(it.userId == AccountService(context).getCurrentUserId().toInt()) {
                    return true
                }
            }
        }
        return false
    }
}