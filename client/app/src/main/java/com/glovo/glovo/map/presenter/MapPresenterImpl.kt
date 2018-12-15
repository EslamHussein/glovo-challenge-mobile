package com.glovo.glovo.map.presenter

import com.glovo.glovo.map.domain.GetCityDetailsUseCase
import com.glovo.glovo.map.domain.GetCountriesUseCase
import com.glovo.glovo.map.view.MainView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MapPresenterImpl(view: MainView, private val countriesUseCase: GetCountriesUseCase) : MapPresenter(view) {
    override fun getCountries() {


        addDisposable(
            countriesUseCase.execute().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).doOnSubscribe {
                    getMyView()?.showLoading()
                }.doFinally {
                    getMyView()?.hideLoading()
                }.subscribe({ countries ->
                    getMyView()?.showCountries(countries)
                }, { error ->
                    getMyView()?.showError(error.localizedMessage)
                })
        )


    }
}