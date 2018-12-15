package com.glovo.glovo.map.presenter

import com.glovo.glovo.base.presenter.BasePresenter
import com.glovo.glovo.map.view.MainView

abstract class MapPresenter(view: MainView)  : BasePresenter<MainView>(view) {
    abstract fun getCountries()
}