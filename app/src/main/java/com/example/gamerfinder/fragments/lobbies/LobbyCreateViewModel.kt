package com.example.gamerfinder.fragments.lobbies

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gamerfinder.R
import com.example.gamerfinder.activities.loginactivity.LoginStatus
import com.example.gamerfinder.utils.ResponseModels
import java.util.regex.Pattern

class LobbyCreateViewModel : ViewModel() {

    private val createLobbyRepository = CreateLobbyRepository()

    private val _createLobbyStatus = MutableLiveData<CreateLobbyStatus>()
    val createLobbyStatus: LiveData<CreateLobbyStatus> = _createLobbyStatus

    private val _createLobbyResult =
        MutableLiveData<LobbyCreateResult<ResponseModels.CreatedLobby>>()
    val createLobbyResult: LiveData<LobbyCreateResult<ResponseModels.CreatedLobby>> =
        _createLobbyResult

    fun createLobby(
        gameId: Int,
        title: String,
        description: String,
        maxUser: String, context: Context
    ) {
        val mUser = isMaxUserValid(maxUser)
        if (mUser <= 1) {
            _createLobbyStatus.value = CreateLobbyStatus(maxUserError = R.string.invalid_max_user)
            return;
        }
        if (!isTitleValid(description)) {
            _createLobbyStatus.value = CreateLobbyStatus(titleError = R.string.invalid_title_empty)
            return
        }
        _createLobbyStatus.value = CreateLobbyStatus(isDataValid = true)

        createLobbyRepository.createLobby(
            gameId,
            title,
            description,
            mUser,
            _createLobbyResult,
            context
        )
    }

    private fun isTitleValid(title: String): Boolean {
        return title.length >= 0
    }

    private fun isMaxUserValid(maxUser: String): Int {
        return maxUser.toIntOrNull() ?: -1
    }
}