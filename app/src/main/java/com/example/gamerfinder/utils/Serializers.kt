package com.example.gamerfinder.utils

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.joda.time.DateTime
import org.joda.time.format.ISODateTimeFormat

@Serializer(forClass = DateTime::class)
object StringNullableDateTimeSerializer : KSerializer<DateTime?> {
    override val descriptor = PrimitiveSerialDescriptor("DateTime", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): DateTime? {
        runCatching {
            return ISODateTimeFormat.dateTime().parseDateTime(decoder.decodeString());
        }.onFailure {
            return null
        }
        return null
    }

    override fun serialize(encoder: Encoder, value: DateTime?) {
        encoder.encodeString(value.toString())
    }
}