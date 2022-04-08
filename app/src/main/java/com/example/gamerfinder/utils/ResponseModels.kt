package com.example.gamerfinder.utils

import kotlinx.serialization.Serializable
import kotlin.reflect.KClassifier


sealed class ResponseModels {

    @Serializable
    class TestsClass(
        val data: List<testsPair>?
    ) {
        override fun toString(): String {
            return "TestsClass(data=$data)"
        }
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