package net.annedawson.seismicalertsystem.network

import com.squareup.moshi.Json

data class FeatureCollection(
    val features: List<Feature>
)

data class Feature(
    val id: String,
    val properties: Properties
)

data class Properties(
    @Json(name = "mag") val magnitude: Double,
    val place: String,
    val time: Long,
    val url: String
)
