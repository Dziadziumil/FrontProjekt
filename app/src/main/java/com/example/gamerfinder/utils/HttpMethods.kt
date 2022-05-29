package com.example.gamerfinder.utils

sealed class HttpGet {
    /*  @Api("tests")
      object TestsGet :
          ApiClient(requestClass = null, ResponseModels.TestsClass::class)


      @Api("users")
      object UsersGet :
          ApiClient(requestClass = null, ListSerializer(ResponseModels.UserFull.serializer()))
  */
}

sealed class HttpPost {

    @Api("Auth")
    //@UseId
    object AuthenticatePost :
        ApiClient<RequestModels.AuthRequest, ResponseModels.AuthResponse>(
            ResponseModels.AuthResponse::class,
        )

    @Api("Users/register")
    object RegisterPost:
        ApiClient<RequestModels.RegisterRequest, ResponseModels.UserFull>(
            ResponseModels.UserFull::class,
        )


}
