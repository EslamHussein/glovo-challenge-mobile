package com.glovo.glovo.map.data.remote.services

import com.glovo.glovo.map.data.dto.City
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface CitiesAPIService {

    @GET("cities")
    fun getAllCities(): Observable<List<City>>

    @GET("cities/{city_code}")
    fun getCityDetails(@Path("city_code") cityCode: String): Observable<City>

}