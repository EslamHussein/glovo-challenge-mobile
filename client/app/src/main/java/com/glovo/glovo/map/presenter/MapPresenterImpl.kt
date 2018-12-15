package com.glovo.glovo.map.presenter

import com.glovo.glovo.base.exception.ErrorHandler
import com.glovo.glovo.map.domain.GetCitiesUseCase
import com.glovo.glovo.map.domain.GetCityDetailsUseCase
import com.glovo.glovo.map.domain.GetCountriesUseCase
import com.glovo.glovo.map.view.MainView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MapPresenterImpl(
    view: MainView, errorHandler: ErrorHandler,
    private val getCountriesUseCase: GetCountriesUseCase,
    private val getCitiesUseCase: GetCitiesUseCase,
    private val getCityDetailsUseCase: GetCityDetailsUseCase
) : MapPresenter(view, errorHandler) {


    override fun getCountries() {


        addDisposable(
            getCountriesUseCase.execute().subscribeOn(Schedulers.io())
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

    override fun getCities() {

        addDisposable(getCitiesUseCase.execute().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).doOnSubscribe {
                getView()?.showLoading()
            }.doFinally {
                getView()?.hideLoading()
            }.subscribe({ cities ->
                getView()?.showCities(cities)
            }, { error ->
                getErrorHandler()?.proceed(error)
            })
        )
    }

    override fun getCityDetails(cityCode: String) {
        addDisposable(getCityDetailsUseCase.execute(GetCityDetailsUseCase.Params.forProjects(cityCode)).subscribeOn(
            Schedulers.io()
        )
            .observeOn(AndroidSchedulers.mainThread()).doOnSubscribe {
                getView()?.showLoading()
            }.doFinally {
                getView()?.hideLoading()
            }.subscribe({ city ->
                getView()?.showCityDetails(city)
            }, { error ->
                getErrorHandler()?.proceed(error)
            })
        )
    }
}