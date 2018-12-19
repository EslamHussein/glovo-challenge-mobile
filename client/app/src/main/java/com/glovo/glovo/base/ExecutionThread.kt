package com.glovo.glovo.base

import io.reactivex.Scheduler

interface ExecutionThread {
    val subscribeScheduler: Scheduler
    val observerScheduler: Scheduler
}