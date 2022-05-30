package com.example.gamerfinder.utils

sealed class HttpGet {
    /*  @Api("tests")
      object TestsGet :
          ApiClient(requestClass = null, ResponseModels.TestsClass::class)


      @Api("users")
      object UsersGet :
          ApiClient(requestClass = null, ListSerializer(ResponseModels.UserFull.serializer()))
  */
    @Api("Users")
    @UseId
    object GetUser :
            ApiClient<Nothing, ResponseModels.UserFull>(
                ResponseModels.UserFull::class,
            )
}

sealed class HttpPut {
    @Api("Users", HttpMethod.PUT)
    @UseId
    //@UseToken
    object UpdateUser :
        ApiClient<RequestModels.UserData, Nothing>(
            null,
        )
}

sealed class HttpPost {

    @Api("Auth")
    //@UseId
    object AuthenticatePost :
        ApiClient<RequestModels.AuthRequest, ResponseModels.AuthResponse>(
            ResponseModels.AuthResponse::class,
        )

    @Api("Users/register")
    object RegisterPost :
        ApiClient<RequestModels.RegisterRequest, ResponseModels.UserFull>(
            ResponseModels.UserFull::class,
        )


}
