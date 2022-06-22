package com.example.gamerfinder.utils

sealed class HttpGet {
    /*  @Api("tests")
      object TestsGet :
          ApiClient(requestClass = null, ResponseModels.TestsClass::class)


      @Api("users")
      object UsersGet :
          ApiClient(requestClass = null, ListSerializer(ResponseModels.UserFull.serializer()))
  */
    @Api("Users", HttpMethod.GET)
    @UseId
    @UseToken
    object GetUser :
        ApiClient<Nothing, ResponseModels.UserFull>(
            ResponseModels.UserFull::class,
        )

    @Api("GameDictionaries", HttpMethod.GET)
    object GetGames :
        ApiClient<Nothing, ResponseModels.GamesList>(
            ResponseModels.GamesList::class,
        )

    @Api("Lobbies", HttpMethod.GET)
    @UseId
    object GetLobbies :
        ApiClient<Nothing, ResponseModels.LoobiesList>(
            ResponseModels.LoobiesList::class
        )

    @Api("Lobby/ownerId", HttpMethod.GET)
    @UseId
    object GetMyLobbies :
        ApiClient<Nothing, ResponseModels.LoobiesList>(
            ResponseModels.LoobiesList::class
        )
}

sealed class HttpDelete {
    @Api("Users", HttpMethod.DELETE)
    @UseId
    object DeleteUser :
            ApiClient<Nothing, Nothing>(
                null
            )
}

sealed class HttpPut {
    @Api("Users", HttpMethod.PUT)
    @UseId
    @UseToken
    object UpdateUser :
        ApiClient<RequestModels.UserData, Nothing>(
            null,
        )

    @Api("Users/password", HttpMethod.PUT)
    @UseId
    @UseToken
    object UpdatePassword :
        ApiClient<RequestModels.UpdatePasswordRequest, Nothing>(
            null,
        )
}

sealed class HttpPost {

    @Api("Lobbies")
    @UseToken
    object CreateLobbyPost :
        ApiClient<RequestModels.CreateLobbyRequest, ResponseModels.CreatedLobby>(
            ResponseModels.CreatedLobby::class
        )

    @Api("Auth")
    //@UseId
    object AuthenticatePost :
        ApiClient<RequestModels.AuthRequest, ResponseModels.AuthResponse>(
            ResponseModels.AuthResponse::class,
        )

    @Api("Users/register")
    object RegisterPost :
        ApiClient<RequestModels.RegisterRequest, ResponseModels.IdToken>(
            ResponseModels.IdToken::class,
        )


}
