package com.glovo.glovo.base.exception

import com.glovo.glovo.base.view.ShowErrorView

interface ErrorHandler {
  fun proceed(error: Throwable)
  fun attachView(view: ShowErrorView)
  fun detachView()
}