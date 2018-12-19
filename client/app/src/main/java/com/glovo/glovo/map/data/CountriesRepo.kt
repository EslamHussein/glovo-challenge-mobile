package com.glovo.glovo.map.data

import com.glovo.glovo.map.data.dto.Country
import io.reactivex.Observable

interface CountriesRepo {

    fun getCountries(): Observable<List<Country>>
}