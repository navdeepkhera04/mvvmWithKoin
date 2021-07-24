package com.squareboat.app

import android.app.Application

import com.squareboat.di.appModule
import com.squareboat.di.repositoryModule
import com.squareboat.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class SquareBoatDemo: Application() {

    companion object {
        var appContext: Application? = null
    }

    override fun onCreate() {
        super.onCreate()
        appContext =this


        startKoin {

            androidLogger()

            androidContext(this@SquareBoatDemo)

            androidFileProperties()

            modules(
                listOf(
                    appModule,
                    viewModelModule,
                    repositoryModule
                )
            )
        }
    }
}