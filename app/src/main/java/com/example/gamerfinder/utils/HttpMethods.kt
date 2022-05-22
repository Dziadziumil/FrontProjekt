package com.example.gamerfinder.utils

import com.example.gamerfinder.activities.loginactivity.User
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.serializer

sealed class HttpGet {

    @Api("tests")
    object TestsGet :
        ApiClient(ResponseModels.TestsClass.serializer())


    @Api("users")
    object UsersGet :
        ApiClient(ListSerializer(UserEntity.serializer()))
}
