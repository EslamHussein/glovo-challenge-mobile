package com.glovo.glovo.selectlocation.presenter

import com.glovo.glovo.base.exception.ErrorHandler
import com.glovo.glovo.map.domain.GetCityDetailsUseCase
import com.glovo.glovo.selectlocation.domain.GetCountriesCitiesUseCase
import com.glovo.glovo.selectlocation.view.SelectLocationView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SelectLocationPresenterImpl(
    view: SelectLocationView, errorHandler: ErrorHandler,
    private val getCountriesCitiesUseCase: GetCountriesCitiesUseCase
) : SelectLocationPresenter(view, errorHandler) {
    override fun getCountriesAndCities() {

        addDisposable(getCountriesCitiesUseCase.execute().subscribeOn(
            Schedulers.io()
        )
            .observeOn(AndroidSchedulers.mainThread()).doOnSubscribe {
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