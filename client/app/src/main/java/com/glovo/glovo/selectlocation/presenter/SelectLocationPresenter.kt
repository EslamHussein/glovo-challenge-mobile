package com.glovo.glovo.selectlocation.presenter

import com.glovo.glovo.base.exception.ErrorHandler
import com.glovo.glovo.base.presenter.BasePresenter
import com.glovo.glovo.selectlocation.view.SelectLocationView

abstract class SelectLocationPresenter(view: SelectLocationView, errorHandler: ErrorHandler) :
    BasePresenter<SelectLocationView>(view, errorHandler) {
    abstract fun getCountriesAndCities()
}