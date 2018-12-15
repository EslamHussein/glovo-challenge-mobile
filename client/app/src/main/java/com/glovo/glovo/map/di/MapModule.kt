package com.glovo.glovo.map.di

import com.glovo.glovo.map.data.remote.repo.CitiesRemoteRepo
import com.glovo.glovo.map.data.remote.repo.CitiesRemoteRepoImpl
import com.glovo.glovo.map.data.remote.repo.CountriesRemoteRepo
import com.glovo.glovo.map.data.remote.repo.CountriesRemoteRepoImpl
import com.glovo.glovo.map.presenter.MapPresenter
import com.glovo.glovo.map.presenter.MapPresenterImpl
import com.glovo.glovo.map.data.remote.services.CitiesAPIService
import com.glovo.glovo.map.data.remote.services.CountriesAPIService
import com.glovo.glovo.map.domain.GetCitiesUseCase
import com.glovo.glovo.map.domain.GetCityDetailsUseCase
import com.glovo.glovo.map.domain.GetCountriesUseCase
import com.glovo.glovo.map.view.MainView
import org.koin.dsl.module.module
import retrofit2.Retrofit

val mapModule = module {

    factory<MapPresenter> { (view: MainView) -> MapPresenterImpl(view, get(), get()) }

    factory { get<Retrofit>().create(CountriesAPIService::class.java) }
    factory { get<Retrofit>().create(CitiesAPIService::class.java) }

    factory { GetCityDetailsUseCase(get()) }
    factory { GetCitiesUseCase(get()) }
    factory { GetCountriesUseCase(get()) }

    single<CountriesRemoteRepo> { CountriesRemoteRepoImpl(get()) }
    single<CitiesRemoteRepo> { CitiesRemoteRepoImpl(get()) }


}

