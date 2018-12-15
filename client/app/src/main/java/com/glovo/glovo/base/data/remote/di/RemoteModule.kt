package com.glovo.glovo.base.data.remote.di

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val remoteModule = module {


    single {
        OkHttpClient().newBuilder().build()
    }

    single { GsonBuilder().create() }

    single {
        Retrofit.Builder()
            .baseUrl(RemoteConfig.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(get()))
            .client(get())
            .build()
    }

}