package com.glovo.glovo.map.domain

import com.glovo.glovo.base.usecase.UseCase
import com.glovo.glovo.map.data.CitiesRepo
import com.glovo.glovo.map.data.dto.City
import io.reactivex.Observable

class GetCityDetailsUseCase(private val citiesRepo: CitiesRepo) :
    UseCase<GetCityDetailsUseCase.Params, Observable<City>> {

    override fun execute(params: Params?): Observable<City> {
        if (params == null) throw IllegalArgumentException("Params can't be null")
        return citiesRepo.getCityDetails(params.cityCode)
    }

    data class Params(val cityCode: String) {
        companion object {
            fun forProjects(cityCode: String): Params {
                return Params(cityCode)
            }
        }
    }
}