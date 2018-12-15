package com.glovo.glovo.map.domain

import com.glovo.glovo.base.usecase.UseCase
import com.glovo.glovo.map.data.dto.City
import com.glovo.glovo.map.data.remote.repo.CitiesRemoteRepo
import io.reactivex.Observable

class GetCitiesUseCase(private val citiesRemoteRepo: CitiesRemoteRepo) : UseCase<Nothing, Observable<List<City>>> {
    override fun execute(params: Nothing?): Observable<List<City>> {
        return citiesRemoteRepo.getCities()
    }
}