package com.glovo.glovo.map.view

import com.glovo.glovo.base.view.ShowErrorView
import com.glovo.glovo.base.view.MvpView
import com.glovo.glovo.map.data.dto.City
import com.glovo.glovo.map.data.dto.Country

interface MainView : MvpView, ShowErrorView {
    fun showLoading()
    fun hideLoading()
    fun showCountries(countries: List<Country>)
    fun showCities(cities: List<City>)
    fun showCityDetails(city: City)
}