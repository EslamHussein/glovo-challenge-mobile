package com.glovo.glovo.base.di

import com.glovo.glovo.base.ResourceManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module


val errorHandler = module {

    factory {
        ResourceManager(androidContext())
    }
}