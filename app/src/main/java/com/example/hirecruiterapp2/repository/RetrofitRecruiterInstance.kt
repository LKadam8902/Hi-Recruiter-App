package com.example.hirecruiterapp2.repository

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitRecruiterInstance {

    val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // Create an OkHttpClient and add the logging interceptor
    val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    val instance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://recruiterapi-jvyb3ct3la-el.a.run.app/agt/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    val recruiterApi: RecruiterApi by lazy {
        instance.create(RecruiterApi::class.java)
    }
}