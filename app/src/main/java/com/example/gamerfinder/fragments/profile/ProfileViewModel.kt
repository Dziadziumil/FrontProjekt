package com.example.gamerfinder.fragments.profile

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gamerfinder.activities.loginactivity.LoginResult
import com.example.gamerfinder.utils.ResponseModels

class ProfileViewModel : ViewModel() {

    private val profileRepository = ProfileRepository()

    private val _user = MutableLiveData<ResponseModels.UserFull>()
    val user: LiveData<ResponseModels.UserFull> = _user

    private val _userUpdateResult = MutableLiveData<LoginResult<Nothing>?>()
    val userUpdateResult: LiveData<LoginResult<Nothing>?> = _userUpdateResult

    fun getUserData(context: Context) {
        profileRepository.getUserData(_user, context)
    }

    fun updateUserData(username: String, firstName: String, secondName: String, email: String, context: Context) {
        profileRepository.updateUserData(_user, username, firstName, secondName, email, _userUpdateResult, context)
    }

    fun updatePassword(currentPassword: String, newPassword: String, context: Context) {

    }

    fun isPasswordValid(currentPassword: String, newPassword: String): Boolean {
        return currentPassword != newPassword && newPassword.length > 6
    }

    fun resetValue() {
        _userUpdateResult.value = null
    }
}