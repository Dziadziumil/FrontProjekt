package com.example.gamerfinder.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gamerfinder.data.LoginDataSourceOg
import com.example.gamerfinder.data.LoginRepositoryOg

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class LoginViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModelOg::class.java)) {
            return LoginViewModelOg(
                    loginRepositoryOg = LoginRepositoryOg(
                            dataSourceOg = LoginDataSourceOg()
                    )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}