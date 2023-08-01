package com.arbelkilani.binge.tv.common.source.remote

import com.arbelkilani.binge.tv.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import java.util.*

class EndpointInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val original = chain.request()
        val originalHttpUrl = original.url

        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("api_key", BuildConfig.API_KEY)
            .addQueryParameter("language", Locale.getDefault().toLanguageTag())
            .addQueryParameter("include_image_language", Locale.getDefault().toLanguageTag())
            .build()

        val request = original.newBuilder()
            .url(url)
            .build()

        return chain.proceed(request)
    }
}