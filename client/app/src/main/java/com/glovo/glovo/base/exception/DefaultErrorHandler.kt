package com.glovo.glovo.base.exception

import com.glovo.glovo.R
import com.glovo.glovo.base.ResourceManager
import com.glovo.glovo.base.view.ShowErrorView
import retrofit2.HttpException
import java.io.IOException
import java.lang.ref.WeakReference
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

internal class DefaultErrorHandler constructor(private val resourceManager: ResourceManager) : ErrorHandler {

    private lateinit var view: WeakReference<ShowErrorView>

    override fun proceed(error: Throwable) {
        val view = view.get()
            ?: throw IllegalStateException("View must be attached to ErrorHandler")
        val message = when (error) {
            is HttpException -> resourceManager.getString(R.string.server_error)
            is IOException, is UnknownHostException, is SocketException, is SocketTimeoutException
            -> resourceManager.getString(R.string.network_error)
            else -> resourceManager.getString(R.string.unknown_error)
        }

        view.showError(message)

    }

    override fun attachView(view: ShowErrorView) {
        this.view = WeakReference(view)
    }

    override fun detachView() {
        view.clear()
    }
}