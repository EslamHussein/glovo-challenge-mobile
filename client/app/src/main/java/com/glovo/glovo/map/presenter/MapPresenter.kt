package com.glovo.glovo.map.presenter

import com.glovo.glovo.base.exception.ErrorHandler
import com.glovo.glovo.base.presenter.BasePresenter
import com.glovo.glovo.map.view.MainView

abstract class MapPresenter(view: MainView,errorHandler: ErrorHandler)  : BasePresenter<MainView>(view,errorHandler) {
    abstract fun getCountries()
}