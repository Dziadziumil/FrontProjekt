package com.example.gamerfinder.activities.loginactivity

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.gamerfinder.utils.*
import java.security.MessageDigest

class LoginRepository {

    fun login(
        username: String,
        password: String,
        _loginResult: MutableLiveData<LoginResult<ResponseModels.AuthResponse>>,
        context: Context
    ) {
        val hash = hashPassword(password)

        val post = HttpPost.AuthenticatePost
        post.addListener(HttpListener(object : Action<ResponseModels.AuthResponse> {
            override fun onMessage(isSuccess: Boolean, value: ResponseModels.AuthResponse?) {
                value?.let {
                    _loginResult.postValue(LoginResult.Success<ResponseModels.AuthResponse>(value))
                }
                if(!isSuccess)
                    _loginResult.postValue(LoginResult.Error("Something went wrong"))
            }
        }))
        
        println("sending request")
        post.requestPost(RequestModels.AuthRequest(username,hash), context)
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