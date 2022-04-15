package com.example.gamerfinder.activities.loginactivity

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gamerfinder.R
import com.example.gamerfinder.utils.Action
import com.example.gamerfinder.utils.HttpGet
import com.example.gamerfinder.utils.HttpListener
import com.example.gamerfinder.utils.ResponseModels
import java.util.regex.Pattern

private val USERNAME: Pattern = Pattern.compile("\\w{3,}")

class LoginViewModel : ViewModel() {

    private val loginRepository = LoginRepository()

    private val _loginStatus = MutableLiveData<LoginStatus>()
    val loginStatus: LiveData<LoginStatus> = _loginStatus

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String) {
        if(!isUsernameValid(username)) {
            _loginStatus.value = LoginStatus(usernameError = R.string.invalid_username)
            return;
        }
        if(!isPasswordValid(password)) {
            _loginStatus.value = LoginStatus(passwordError = R.string.invalid_password)
            return
        }
        _loginStatus.value = LoginStatus(isDataValid = true)

        val result = loginRepository.login(username, password)

        _loginResult.value = result
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 6
    }

    private fun isUsernameValid(username: String): Boolean {
        return if(username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            USERNAME.matcher(username).matches()
        }
    }
}