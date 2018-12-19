package com.glovo.glovo.map.data.remote.repo

import com.glovo.glovo.map.data.dto.City
import com.glovo.glovo.map.data.dto.Country
import io.reactivex.Observable

interface CountriesRemoteRepo {

    fun getCountries(): Observable<List<Country>>
}