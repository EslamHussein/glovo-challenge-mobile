package com.glovo.glovo.base.exception


import io.reactivex.Observable
import io.reactivex.functions.Function


object ErrorManager {

    fun <T> wrap(observable: Observable<T>): Observable<T> {
        return observable.onErrorResumeNext(ExceptionsInterceptor())
    }

    /**
     * Maps java exceptions to the appropriate [AppException]
     */
    private class ExceptionsInterceptor<T> : Function<Throwable, Observable<T>> {


        @Throws(Exception::class)
        override fun apply(throwable: Throwable): Observable<T> {
            return Observable.error(AppException.adapt(throwable))
        }
    }

}
