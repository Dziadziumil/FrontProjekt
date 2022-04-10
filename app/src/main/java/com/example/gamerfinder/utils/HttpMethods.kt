package com.example.gamerfinder.utils

import kotlinx.serialization.KSerializer

sealed class HttpGet {

    @Api("tests")
    object TestsGet :
        ApiClient(ResponseModels.TestsClass.serializer())


    @Api("users")
    object UsersGet :
        ApiClient(ResponseModels.UsersClass.serializer())
}
