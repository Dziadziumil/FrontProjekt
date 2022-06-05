@file:UseSerializers(StringNullableDateTimeSerializer::class)

package com.example.gamerfinder.utils

import kotlinx.serialization.UseSerializers

import kotlinx.serialization.Serializable


sealed class ResponseModels {

    @Serializable
    class TestsClass(
        val data: List<testsPair>?
    ) {
        override fun toString(): String {
            return "TestsClass(data=$data)"
        }
    }

    @Serializable
    data class IdToken(
        val id: Int?,
        val token: String?
    ) {
        override fun toString(): String {
            return "IdToken(id=$id, token=$token)"
        }
    }


    @Serializable
    data class UserFull(
        val id: Int? = null,
        val userName: String?,
        val firstName: String?,
        val secondName: String?,
        val birthDate: String?,
        val email: String?,
        val gender: String?
    ) {
        override fun toString(): String {
            return "UserFull(id=$id, userName=$userName, firstName=$firstName, secondName=$secondName, birthDate=$birthDate, email=$email, gender=$gender)"
        }
    }

    @Serializable
    data class AuthResponse(
        val id: Int? = null,
        val token: String? = null
    ) {
        override fun toString(): String {
            return "RegisterResponse(id=$id, token=$token)"
        }
    }

}


class RequestModels {

    @Serializable
    data class UserData(
        val id: Int,
        val userName: String?,
        val firstName: String?,
        val secondName: String?,
        val birthDate: String?,
        val gender: String?
    ) : BaseModel() {
        override fun toString(): String {
            return "UserFull(userName=$userName, firstName=$firstName, secondName=$secondName, birthDate=$birthDate, gender=$gender)"
        }
    }

    @Serializable
    data class AuthRequest(
        val UserEmail: String?,
        val PasswordHash: String?
    ) : BaseModel() {
        override fun toString(): String {
            return "AuthRequest(UserEmail=$UserEmail, PasswordHash=$PasswordHash)"
        }
    }

    @Serializable
    data class RegisterRequest(
        val email: String?,
        val userName: String?,
        val passwordHash: String?
    ) : BaseModel() {
        override fun toString(): String {
            return "RegisterRequest(email=$email, userName=$userName, passwordHash=$passwordHash)"
        }
    }

    @Serializable
    data class UpdatePasswordRequest(
        val passwordHash: String?,
        val NewPasswordHash: String?
    ) : BaseModel() {
        override fun toString(): String {
            return "UpdatePasswordRequest(passwordHash=$passwordHash, NewPasswordHash=$NewPasswordHash)"
        }
    }


    @Serializable
    sealed class BaseModel
}


@Serializable
class testsPair(
    val id: Int?,
    val attributes: AttributesTests?
) {
    override fun toString(): String {
        return "testsPair(id=$id, attributes=$attributes)"
    }


}

@Serializable
class AttributesTests(
    val testColumn: String?,
    val testNumber: Int?,
    val createdAt: String?,
    val updatedAt: String?,
    val publishedAt: String?
) {
    override fun toString(): String {
        return "AttributesTests(testColumn=$testColumn, testNumber=$testNumber, createdAt=$createdAt, updatedAt=$updatedAt, publishedAt=$publishedAt)"
    }


}