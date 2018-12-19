package com.glovo.glovo

import android.app.Application
import com.glovo.glovo.base.data.remote.di.remoteModule
import com.glovo.glovo.base.di.errorHandler
import com.glovo.glovo.base.di.managersModule
import com.glovo.glovo.map.di.mapModule
import com.glovo.glovo.selectlocation.di.selectLocationModule
import org.koin.android.ext.android.startKoin

class GlovoApp : Application() {


    override fun onCreate() {
        super.onCreate()

        instance = this
        startKoin(this, listOf(mapModule, remoteModule, errorHandler, managersModule, selectLocationModule))

    }


    companion object {

        private var instance: GlovoApp? = null

        fun get(): GlovoApp {
            if (instance == null)
                throw IllegalStateException("Something went horribly wrong!!, no application attached!")
            return instance as GlovoApp
        }
    }


}