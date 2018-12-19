package com.glovo.glovo.selectlocation.presenter

import com.glovo.glovo.base.ExecutionThread
import com.glovo.glovo.base.exception.ErrorHandler
import com.glovo.glovo.selectlocation.domain.GetCountriesCitiesUseCase
import com.glovo.glovo.selectlocation.view.SelectLocationView

class SelectLocationPresenterImpl(
    view: SelectLocationView, errorHandler: ErrorHandler,
    private val getCountriesCitiesUseCase: GetCountriesCitiesUseCase,
    private val executionThread: ExecutionThread
) : SelectLocationPresenter(view, errorHandler) {
    override fun getCountriesAndCities() {

        addDisposable(getCountriesCitiesUseCase.execute().subscribeOn(executionThread.subscribeScheduler)
            .observeOn(executionThread.observerScheduler).doOnSubscribe {
                getView()?.showLoading()
            }.doFinally {
                getView()?.hideLoading()
            }.subscribe({ locations ->
                getView()?.showCountriesCities(locations)
            }, { error ->
                getErrorHandler()?.proceed(error)
            })
        )
    }


}