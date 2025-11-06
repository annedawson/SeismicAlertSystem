package net.annedawson.seismicalertsystem.data

data class Earthquake(
    val id: String,
    val magnitude: Double,
    val place: String,
    val time: Long,
    val url: String
)