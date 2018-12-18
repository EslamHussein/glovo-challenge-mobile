package com.glovo.glovo.base.di

import android.app.Activity
import com.glovo.glovo.base.exception.DefaultErrorHandler
import com.glovo.glovo.base.exception.ErrorHandler
import com.glovo.glovo.util.PermissionManager
import org.koin.dsl.module.module

val managersModule = module {
    factory<ErrorHandler> {
        DefaultErrorHandler(get())
    }

    factory { (activity: Activity) ->
        PermissionManager(activity)
    }
}