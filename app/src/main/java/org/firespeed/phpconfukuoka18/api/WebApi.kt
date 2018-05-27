package org.firespeed.phpconfukuoka18.api

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.firespeed.phpconfukuoka18.model.Session
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.net.CookieHandler
import java.net.CookieManager
import java.net.CookiePolicy


class WebApi {

    fun getContent(): Single<List<Session>> {
        return httpClient.getContent().subscribeOn(Schedulers.io())
                .map(JsoupMapper())
                .map(ContentMapper())
                .singleOrError()

    }


    private fun createOkHttpClient(): OkHttpClient {
        val cookieManager = CookieManager()
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL)
        CookieHandler.setDefault(cookieManager)

        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL)
        return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor())
                .build()
    }

    private fun loggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        return loggingInterceptor
    }

    private val httpClient: HttpApi by lazy {
        val builder = Retrofit.Builder()
                .client(createOkHttpClient())
                .baseUrl("https://phpcon.fukuoka.jp/2018/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
        builder.create(HttpApi::class.java)
    }
}