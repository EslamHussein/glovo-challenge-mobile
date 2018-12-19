package com.glovo.glovo.map.data.remote.repo

import com.glovo.glovo.map.data.dto.City
import io.reactivex.Observable

interface CitiesRemoteRepo {
    fun getCities(): Observable<List<City>>
    fun getCityDetails(cityCode: String): Observable<City>

}