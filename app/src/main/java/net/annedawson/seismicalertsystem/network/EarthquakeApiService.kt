package net.annedawson.seismicalertsystem.network

import retrofit2.http.GET

interface EarthquakeApiService {
    @GET("earthquakes/feed/v1.0/summary/all_hour.geojson")
    suspend fun getEarthquakes(): FeatureCollection
}