package com.example.gamerfinder.fragments.lobbies

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.gamerfinder.utils.*

class CreateLobbyRepository {

    fun createLobby(
        gameId: Int,
        title: String,
        description: String,
        maxUser: Int,
        _createLobbyResult: MutableLiveData<LobbyCreateResult<ResponseModels.CreatedLobby>>,
        context: Context
    ) {
        val post = HttpPost.CreateLobbyPost
        post.addListener(HttpListener(object : Action<ResponseModels.CreatedLobby> {
            override fun onMessage(isSuccess: Boolean, value: ResponseModels.CreatedLobby?) {
                value?.let {
                    _createLobbyResult.postValue(LobbyCreateResult.Success(value))
                }
                if (!isSuccess)
                    _createLobbyResult.postValue(LobbyCreateResult.Error("Something went wrong"))
            }
        }))

        println("sending request")
        post.requestPost(
            RequestModels.CreateLobbyRequest(
                gameId = gameId,
                ownerId = AccountService(context).getCurrentUserId().toInt(),
                title = title,
                description = description,
                maxUser = maxUser
            ), context
        )
    }
}