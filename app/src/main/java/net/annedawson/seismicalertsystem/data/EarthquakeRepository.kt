package net.annedawson.seismicalertsystem.data

import net.annedawson.seismicalertsystem.network.RetrofitInstance

/**
 * Fetches earthquake data from the remote API and converts it to domain objects.
 */
suspend fun getEarthquakes(): List<Earthquake> {
    return RetrofitInstance.api.getEarthquakes().asDomainObjects()
}

/**
 * Converts a [net.annedawson.seismicalertsystem.network.FeatureCollection] to a list of [Earthquake] domain objects.
 */
fun net.annedawson.seismicalertsystem.network.FeatureCollection.asDomainObjects(): List<Earthquake> {
    return features.map {
        Earthquake(
            id = it.id,
            magnitude = it.properties.magnitude,
            place = it.properties.place,
            time = it.properties.time,
            url = it.properties.url
        )
    }
}