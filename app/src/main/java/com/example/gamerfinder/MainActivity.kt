package com.example.gamerfinder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.gamerfinder.utils.UserPreferences
import com.example.gamerfinder.databinding.ActivityMainBinding
import com.example.gamerfinder.utils.Configs

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var userPreferences: UserPreferences

    private var _authToken: String? = null
    private val authToken get() = _authToken

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Configs.initProperties(applicationContext)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        userPreferences = UserPreferences(applicationContext)
        _authToken = userPreferences.authToken
//        if(authToken != null)
//            Toast.makeText(applicationContext, authToken, Toast.LENGTH_SHORT).show()

        //setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}