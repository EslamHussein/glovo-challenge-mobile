package com.glovo.glovo.map.data.remote.repo

import com.glovo.glovo.map.data.dto.City
import com.glovo.glovo.map.data.remote.services.CitiesAPIService
import io.reactivex.Observable

class CitiesRemoteRepoImpl(private val citiesAPIService: CitiesAPIService) : CitiesRemoteRepo {
    override fun getCities(): Observable<List<City>> = citiesAPIService.getAllCities()

    override fun getCityDetails(cityCode: String): Observable<City> {
        return citiesAPIService.getCityDetails(cityCode)
    }
}