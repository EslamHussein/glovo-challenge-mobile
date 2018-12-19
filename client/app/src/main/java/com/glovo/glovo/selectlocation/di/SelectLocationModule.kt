package com.glovo.glovo.selectlocation.di

import com.glovo.glovo.selectlocation.domain.GetCountriesCitiesUseCase
import com.glovo.glovo.selectlocation.presenter.SelectLocationPresenter
import com.glovo.glovo.selectlocation.presenter.SelectLocationPresenterImpl
import com.glovo.glovo.selectlocation.view.SelectLocationView
import org.koin.dsl.module.module


val selectLocationModule = module {

    factory<SelectLocationPresenter> { (view: SelectLocationView) ->
        SelectLocationPresenterImpl(view, get(), get(), get())
    }

    factory { GetCountriesCitiesUseCase(get(), get()) }
}