package com.glovo.glovo.map.domain

import com.glovo.glovo.base.usecase.UseCase
import com.glovo.glovo.map.data.CitiesRepo
import com.glovo.glovo.map.data.dto.City
import io.reactivex.Observable

class GetCitiesUseCase(private val citiesRepo: CitiesRepo) : UseCase<Nothing, Observable<List<City>>> {
    override fun execute(params: Nothing?): Observable<List<City>> {
        return citiesRepo.getCities()
    }
}