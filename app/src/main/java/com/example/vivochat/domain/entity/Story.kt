package com.example.vivochat.domain.entity

import com.google.firebase.Timestamp
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
data class Story(
    val storyId: String,
    val imageUrl:String,
    val watchedBy:List<String>?,
    @Serializable(with = FirebaseTimestampSerializer::class)
    val date : Timestamp
)

object FirebaseTimestampSerializer : KSerializer<Timestamp> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("FirebaseTimestamp", PrimitiveKind.LONG)

    override fun serialize(encoder: Encoder, value: Timestamp) {
        encoder.encodeLong(value.seconds) // store timestamp as seconds
    }

    override fun deserialize(decoder: Decoder): Timestamp {
        val seconds = decoder.decodeLong()
        return Timestamp(seconds, 0) // nanos = 0
    }
}
