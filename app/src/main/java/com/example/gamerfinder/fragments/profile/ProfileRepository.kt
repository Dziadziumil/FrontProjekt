package com.example.gamerfinder.fragments.profile

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.gamerfinder.activities.loginactivity.LoginResult
import com.example.gamerfinder.utils.*
import java.security.MessageDigest

class ProfileRepository {

    fun getUserData(_user: MutableLiveData<ResponseModels.UserFull>, context: Context) {
        HttpGet.GetUser.apply {
            this.addListener(HttpListener(object : Action<ResponseModels.UserFull> {
                override fun onMessage(isSuccess: Boolean, value: ResponseModels.UserFull?) {
                    if(isSuccess) {
                        value?.let {
                            _user.postValue(value)
                        }
                    } else {
                        println("couldn't get user data")
                    }
                }
            }))
        }.requestGet(context)
    }

    fun updateUserData(
        _user: MutableLiveData<ResponseModels.UserFull>,
        username: String,
        firstName: String,
        secondName: String,
        email: String,
        _userUpdateResult: MutableLiveData<LoginResult<Nothing>?>,
        context: Context
    ) {
        HttpPut.UpdateUser.apply {
            this.addListener(HttpListener(object : Action<Nothing> {
                override fun onMessage(isSuccess: Boolean, value: Nothing?) {
                    if(isSuccess) {
                        _userUpdateResult.postValue(LoginResult.Success(null))
                    } else {
                        _userUpdateResult.postValue(LoginResult.Error("couldn't update user"))
                    }
                }
            }))
        }.requestPost(
            RequestModels.UserData(
                AccountService(context).getCurrentUserId().toInt(),
                username,
                firstName,
                secondName,
                _user.value?.birthDate,
                _user.value?.gender
            ),
            context
        )
    }

    fun updatePassword(id: Int, oldPassword: String, newPassword: String) {
        // TODO call api to change password
        val hash = hashPassword(oldPassword)
        val newHash = hashPassword(newPassword)
    }

    private fun hashPassword(password: String): String {
        return MessageDigest
            .getInstance("SHA-256")
            .digest(password.toByteArray())
            .fold("") {
                    str, byte -> str + "%02x".format(byte)
            }
    }
}