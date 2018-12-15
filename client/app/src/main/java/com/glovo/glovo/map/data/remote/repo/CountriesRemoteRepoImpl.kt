package com.glovo.glovo.map.data.remote.repo

import com.glovo.glovo.map.data.dto.Country
import com.glovo.glovo.map.data.remote.services.CountriesAPIService
import io.reactivex.Observable

class CountriesRemoteRepoImpl(private val countriesAPIService: CountriesAPIService) : CountriesRemoteRepo {

    override fun getCountries(): Observable<List<Country>> = countriesAPIService.getAllCountries()


}