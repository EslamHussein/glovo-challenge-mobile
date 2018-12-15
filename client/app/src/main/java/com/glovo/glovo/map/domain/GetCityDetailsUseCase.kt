package com.glovo.glovo.map.domain

import com.glovo.glovo.base.usecase.UseCase
import com.glovo.glovo.map.data.dto.City
import com.glovo.glovo.map.data.remote.repo.CitiesRemoteRepo
import io.reactivex.Observable

class GetCityDetailsUseCase(private val citiesRemoteRepo: CitiesRemoteRepo) :
    UseCase<GetCityDetailsUseCase.Params, Observable<City>> {

    override fun execute(params: Params?): Observable<City> {
        if (params == null) throw IllegalArgumentException("Params can't be null")
        return citiesRemoteRepo.getCityDetails(params.cityCode)
    }

    data class Params(val cityCode: String) {
        companion object {
            fun forProjects(cityCode: String): Params {
                return Params(cityCode)
            }
        }
    }
}