package com.glovo.glovo.map.data

import com.glovo.glovo.map.data.dto.Country
import com.glovo.glovo.map.data.remote.repo.CountriesRemoteRepo
import io.reactivex.Observable

class CountriesRepoImpl(private val countriesAPIService: CountriesRemoteRepo) : CountriesRepo {

    override fun getCountries(): Observable<List<Country>> = countriesAPIService.getCountries()


}