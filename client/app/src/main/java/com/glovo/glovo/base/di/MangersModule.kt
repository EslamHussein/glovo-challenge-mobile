package com.glovo.glovo.base.di

import com.glovo.glovo.base.exception.DefaultErrorHandler
import com.glovo.glovo.base.exception.ErrorHandler
import org.koin.dsl.module.module

val managersModule = module {
    factory<ErrorHandler> {
        DefaultErrorHandler(get())
    }
}