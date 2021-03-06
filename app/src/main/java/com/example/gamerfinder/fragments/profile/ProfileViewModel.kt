package com.example.gamerfinder.fragments.profile

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gamerfinder.R
import com.example.gamerfinder.activities.loginactivity.LoginResult
import com.example.gamerfinder.utils.*
import java.security.MessageDigest

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    init {
        getUserData(application.applicationContext)
    }

    private val _user = MutableLiveData<ResponseModels.UserFull>()
    val user: LiveData<ResponseModels.UserFull> = _user

    private val _userUpdateResult = MutableLiveData<LoginResult<Nothing>?>()
    val userUpdateResult: LiveData<LoginResult<Nothing>?> = _userUpdateResult

    private val _passwordUpdateResult = MutableLiveData<LoginResult<Nothing>?>()
    val passwordUpdateResult: LiveData<LoginResult<Nothing>?> = _passwordUpdateResult

    private val _deleteAccountResult = MutableLiveData<LoginResult<Nothing>>()
    val deleteAccountResult: LiveData<LoginResult<Nothing>> = _deleteAccountResult

    fun getUserData(context: Context) {
        HttpGet.GetUser.apply {
            this.addListener(HttpListener(object : Action<ResponseModels.UserFull> {
                override fun onMessage(isSuccess: Boolean, value: ResponseModels.UserFull?) {
                    if(isSuccess){
                        _user.postValue(value!!)
                    } else {
                        println("couldn't get user data")
                    }
                }
            }))
        }.requestGet(context, AccountService(context).getCurrentUserId())
    }

    fun updateUserData(
        username: String,
        firstName: String,
        secondName: String,
        email: String,
        birthDate: String,
        gender: String,
        context: Context) {
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
                birthDate,
                gender
            ),
            context,
            AccountService(context).getCurrentUserId()
        )
    }

    fun updatePassword(currentPassword: String, newPassword: String, context: Context) {
        val passwordHash = hashPassword(currentPassword)
        val newPasswordHash = hashPassword(newPassword)

        HttpPut.UpdatePassword.apply {
            this.addListener(HttpListener(object : Action<Nothing> {
                override fun onMessage(isSuccess: Boolean, value: Nothing?) {
                    if(isSuccess) {
                        _passwordUpdateResult.postValue(LoginResult.Success(null))
                    } else {
                        _passwordUpdateResult.postValue(LoginResult.Error("couldn't update password"))
                    }
                }
            }))
        }.requestPost(
            RequestModels.UpdatePasswordRequest(
                passwordHash,
                newPasswordHash
            ),
            context,
            AccountService(context).getCurrentUserId()
        )
    }

    fun isPasswordValid(currentPassword: String, newPassword: String): Boolean {
        return currentPassword != newPassword && newPassword.length > 1
    }

    fun deleteAccount(context: Context) {
        HttpDelete.DeleteUser.apply {
            this.addListener(HttpListener(object : Action<Nothing> {
                override fun onMessage(isSuccess: Boolean, value: Nothing?) {
                    if(isSuccess) {
                        _deleteAccountResult.postValue(LoginResult.Success(null))
                    } else {
                        _deleteAccountResult.postValue(LoginResult.Error("couldn't delete account"))
                    }
                }
            }))
        }.requestGet(context, AccountService(context).getCurrentUserId())
    }

    private fun hashPassword(password: String): String {
        return MessageDigest
            .getInstance("SHA-256")
            .digest(password.toByteArray())
            .fold("") {
                    str, byte -> str + "%02x".format(byte)
            }
    }

    fun resetValue() {
        _userUpdateResult.value = null
        _passwordUpdateResult.value = null
    }
}