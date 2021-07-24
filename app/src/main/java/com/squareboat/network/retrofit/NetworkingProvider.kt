package com.squareboat.network.retrofit

import com.squareboat.util.Const
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun provideLoggingInterceptor(): HttpLoggingInterceptor {
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY
    return logging
}

var appInterceptor: Interceptor? = null
fun provideHttpClient(logging: HttpLoggingInterceptor): OkHttpClient {
    val httpClient = OkHttpClient.Builder()
    if (appInterceptor == null)
        appInterceptor = MyAppInterceptor()

    httpClient.addInterceptor(appInterceptor!!)
    httpClient.addInterceptor(logging)
    return httpClient.build()
}

fun provideApiProvider(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(Const.SERVER_REMOTE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun provideApiService(retrofit: Retrofit): ApiService {
    return retrofit.create(ApiService::class.java)
}
