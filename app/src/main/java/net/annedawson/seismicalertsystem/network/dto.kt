package net.annedawson.seismicalertsystem.network

import com.squareup.moshi.Json

/**
 * Data Transfer Object (DTO) for the entire JSON response from the earthquake API.
 * It contains a list of earthquake features.
 *
 * @property features A list of [Feature] objects, each representing an earthquake event.
 */
data class FeatureCollection(
    val features: List<Feature>
)

/**
 * Represents a single earthquake event feature from the API.
 *
 * @property id A unique identifier for the earthquake event.
 * @property properties The [Properties] object containing detailed information about the earthquake.
 */
data class Feature(
    val id: String,
    val properties: Properties
)

/**
 * Contains the specific properties of an earthquake event.
 *
 * @property magnitude The magnitude of the earthquake. The @Json annotation is used to map the JSON key "mag" to this property.
 * @property place A description of the location of the earthquake.
 * @property time The time the earthquake occurred, in milliseconds since the epoch.
 * @property url A URL to a webpage with more information about the earthquake.
 */
data class Properties(
    @Json(name = "mag") val magnitude: Double,
    val place: String,
    val time: Long,
    val url: String
)
