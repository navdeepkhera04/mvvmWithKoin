package com.squareboat.di

import android.content.Context
import com.squareboat.app.SquareBoatDemo
import com.squareboat.network.retrofit.provideApiProvider
import com.squareboat.network.retrofit.provideApiService
import com.squareboat.network.retrofit.provideHttpClient
import com.squareboat.network.retrofit.provideLoggingInterceptor
import org.koin.dsl.module


val appModule = module {

    provideAppContext()


    single {
        provideLoggingInterceptor()
    }
    single {
        provideHttpClient(get())
    }
    single {
        provideApiProvider(get())
    }
    single {
        provideApiService(get())
    }

    single {

    }
}

// context provider
fun provideAppContext(): Context {
    return SquareBoatDemo.appContext!!
}



