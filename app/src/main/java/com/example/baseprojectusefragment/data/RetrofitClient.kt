package com.example.baseprojectusefragment.data

import com.example.baseprojectusefragment.BuildConfig
import com.google.gson.GsonBuilder
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient private constructor(baseUrl: String = BASE_URL) {
    private val retrofit: Retrofit by lazy {
        val client = OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(
                LoggingInterceptor.Builder()
                    .loggable(BuildConfig.DEBUG)
                    .setLevel(Level.BASIC)
                    .request("Request")
                    .response("Response")
                    .build()
            )

        Retrofit.Builder()
            .client(client.build())
            .baseUrl(baseUrl)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    private val interfaceCache = HashMap<Class<*>, Any>()

    @Suppress("UNCHECKED_CAST")
    operator fun <T : Any> get(classOfService: Class<T>): T =
        (interfaceCache[classOfService] as? T) ?: retrofit.create(classOfService).also {
            interfaceCache[classOfService] = it
        }

    companion object {
        private const val TIMEOUT = 300L
        private const val ISO_8601_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ"
        private var INSTANCE: RetrofitClient? = null

        fun getInstance(): RetrofitClient = INSTANCE ?: RetrofitClient().also {
            INSTANCE = it
        }

        const val BASE_URL = "https://www.coinhako.com/api/v3/"
        private val gsonConverterFactory = GsonConverterFactory.create(
            GsonBuilder().setDateFormat(ISO_8601_DATE_TIME_FORMAT).setLenient()
                .create()
        )
    }
}