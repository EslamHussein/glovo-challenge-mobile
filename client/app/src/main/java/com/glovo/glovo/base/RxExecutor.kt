package com.glovo.glovo.base

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RxExecutor : ExecutionThread {
    override val subscribeScheduler: Scheduler
        get() = Schedulers.io()
    override val observerScheduler: Scheduler
        get() = AndroidSchedulers.mainThread()
}



