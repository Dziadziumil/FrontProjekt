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
    data class UserFull(
        val id: Int?,
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
        val id: Int?,
        val token: String?
    ) {
        override fun toString(): String {
            return "RegisterResponse(id=$id, token=$token)"
        }
    }

}


class RequestModels {

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