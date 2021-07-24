package com.squareboat.network.retrofit

import com.squareboat.app.SquareBoatDemo
import com.squareboat.util.Const
import com.squareboat.util.Prefs
import okhttp3.Interceptor
import okhttp3.Response


class MyAppInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        var request = chain.request()
        val headers = request.headers.newBuilder()
            .add("Content-Type", "application/json")
            .add("Accept", "application/json")
            .add(
                "authorization",
                Prefs.with(SquareBoatDemo.appContext)!!.getString(Const.USER_TOKEN, "")!!
            )
            .build()

        request = request.newBuilder().headers(headers).build()

        return chain.proceed(request)
    }
}