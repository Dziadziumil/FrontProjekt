@file:UseSerializers(StringNullableDateTimeSerializer::class)

package com.example.gamerfinder.utils

import kotlinx.serialization.UseSerializers

import kotlinx.serialization.Serializable
import org.joda.time.DateTime


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
    class UsersClass(
        val users: List<UserEntity>
    )

}

@Serializable
class UserEntity(
    val id: Int?,
    val firstName: String?,
    val secondName: String?,
    val birthDate: DateTime?,
    val phone: Int?,
    val email: String?,
    val passwordHash: String?
) {
    override fun toString(): String {
        return "UserEntity(id=$id, firstName=$firstName, secondName=$secondName, birthDate=$birthDate, phone=$phone, email=$email, passwordHash=$passwordHash)"
    }
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