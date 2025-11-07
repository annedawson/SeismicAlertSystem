package net.annedawson.seismicalertsystem.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * A singleton object that provides a configured Retrofit instance for making network requests.
 * Using an object declaration ensures that there is only one instance of Retrofit throughout the app,
 * which is an efficient way to manage network resources.
 */
object RetrofitInstance {
    // The base URL for the earthquake API.
    private const val BASE_URL = "https://earthquake.usgs.gov/"

    /**
     * A Moshi instance used to parse the JSON response from the API into Kotlin objects.
     * Moshi is a modern JSON library for Android and Java.
     * It makes it easy to parse JSON into data classes.
     */
    private val moshi = Moshi.Builder()
        // The KotlinJsonAdapterFactory allows Moshi to understand and work with Kotlin-specific language features like null safety and default values.
        .add(KotlinJsonAdapterFactory())
        .build()

    /**
     * A lazily initialized Retrofit API service.
     * `lazy` means the Retrofit instance will only be created the first time it is accessed, not when the app starts.
     * This is a performance optimization.
     */
    val api: EarthquakeApiService by lazy {
        // The Retrofit.Builder is used to configure and build the Retrofit instance.
        Retrofit.Builder()
            // Sets the base URL for all API requests.
            .baseUrl(BASE_URL)
            // Adds a converter factory that tells Retrofit how to process the data it receives.
            // In this case, it uses MoshiConverterFactory to parse JSON responses using the `moshi` instance configured above.
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            // Builds the Retrofit instance.
            .build()
            // Creates an implementation of the `EarthquakeApiService` interface.
            .create(EarthquakeApiService::class.java)
    }
}