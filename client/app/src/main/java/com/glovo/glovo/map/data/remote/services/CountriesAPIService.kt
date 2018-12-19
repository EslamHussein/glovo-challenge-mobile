package com.glovo.glovo.map.data.remote.services

import com.glovo.glovo.map.data.dto.Country
import io.reactivex.Observable
import retrofit2.http.GET


interface CountriesAPIService {
    @GET("countries")
    fun getAllCountries(): Observable<List<Country>>

}