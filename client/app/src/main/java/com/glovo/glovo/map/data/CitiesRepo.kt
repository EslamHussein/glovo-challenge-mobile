package com.glovo.glovo.map.data

import com.glovo.glovo.map.data.dto.City
import io.reactivex.Observable

interface CitiesRepo {
    fun getCities(): Observable<List<City>>
    fun getCityDetails(cityCode: String): Observable<City>

}