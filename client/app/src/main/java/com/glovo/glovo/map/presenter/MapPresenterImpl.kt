package com.glovo.glovo.map.presenter

import com.glovo.glovo.base.exception.ErrorHandler
import com.glovo.glovo.map.domain.GetCountriesUseCase
import com.glovo.glovo.map.view.MainView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MapPresenterImpl(
    view: MainView, private val countriesUseCase: GetCountriesUseCase,
    errorHandler: ErrorHandler
) : MapPresenter(view, errorHandler) {

    override fun getCountries() {


        addDisposable(
            countriesUseCase.execute().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).doOnSubscribe {
                    getView()?.showLoading()
                }.doFinally {
                    getView()?.hideLoading()
                }.subscribe({ countries ->
                    getView()?.showCountries(countries)
                }, { error ->
                    getErrorHandler()?.proceed(error)
                })
        )

    }
}