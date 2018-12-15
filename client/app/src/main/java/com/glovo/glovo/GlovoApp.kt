package com.glovo.glovo

import android.app.Application
import com.glovo.glovo.base.data.remote.di.remoteModule
import com.glovo.glovo.map.di.mapModule
import org.koin.android.ext.android.startKoin

class GlovoApp : Application() {


    override fun onCreate() {
        super.onCreate()

        instance = this
        startKoin( this,listOf(mapModule, remoteModule))

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