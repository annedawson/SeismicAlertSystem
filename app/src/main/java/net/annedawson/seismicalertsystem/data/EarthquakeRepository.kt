package net.annedawson.seismicalertsystem.data

import net.annedawson.seismicalertsystem.network.RetrofitInstance

/**
 * Fetches earthquake data from the remote API and converts it to a list of [Earthquake] domain objects.
 *
 * This is a suspend function, meaning it can be paused and resumed at a later time. This is useful for long-running operations like network requests,
 * as it prevents blocking the main thread and keeps the app responsive.
 *
 * @return A list of [Earthquake] objects.
 */
suspend fun getEarthquakes(): List<Earthquake> {
    // Makes a network request to the earthquake API using the Retrofit instance.
    val featureCollection = RetrofitInstance.api.getEarthquakes()
    // Converts the response from the API (a FeatureCollection) into a list of Earthquake domain objects.
    return featureCollection.asDomainObjects()
}

/**
 * Converts a [net.annedawson.seismicalertsystem.network.FeatureCollection] from the network layer to a list of [Earthquake] domain objects for the app to use.
 *
 * This is an extension function, which means it adds new functionality to an existing class without having to inherit from it.
 * In this case, it adds the `asDomainObjects` method to the `FeatureCollection` class.
 *
 * @return A list of [Earthquake] objects.
 */
fun net.annedawson.seismicalertsystem.network.FeatureCollection.asDomainObjects(): List<Earthquake> {
    // The `map` function iterates over the `features` list and applies a transformation to each item.
    return features.map {
        // For each feature from the network response, create a new `Earthquake` object.
        Earthquake(
            id = it.id,
            magnitude = it.properties.magnitude,
            place = it.properties.place,
            time = it.properties.time,
            url = it.properties.url
        )
    }
}