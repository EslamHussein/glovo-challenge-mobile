package com.glovo.glovo.base.exception

import androidx.annotation.IntDef
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class AppException constructor(@param:ErrorCode @get:ErrorCode
                               val errorCode: Int, val errorMessage: String, val original: Throwable? = Exception(errorMessage)) : Exception(original) {

    @IntDef(NETWORK_ERROR, NO_DATA_ERROR, UNKNOWN_ERROR)
    @kotlin.annotation.Retention(value = AnnotationRetention.SOURCE)
    annotation class ErrorCode

    companion object {

        const val NETWORK_ERROR = 1
        const val NO_DATA_ERROR = 2
        const val UNKNOWN_ERROR = 3


        fun adapt(t: Throwable): Throwable {
            return if (t is UnknownHostException || t is SocketException || t is SocketTimeoutException) {
                AppException(NETWORK_ERROR, "Please check your internet connection and try again.", t)
            } else {
                AppException(UNKNOWN_ERROR, "Oops … something went wrong!", t)
            }
        }
    }

}
