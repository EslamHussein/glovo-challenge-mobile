package com.glovo.glovo.map.domain

import com.glovo.glovo.base.usecase.UseCase
import com.glovo.glovo.map.data.dto.Country
import com.glovo.glovo.map.data.CountriesRepo
import io.reactivex.Observable

class GetCountriesUseCase(private val countriesRepo: CountriesRepo) :
    UseCase<Nothing, Observable<List<Country>>> {

    override fun execute(params: Nothing?): Observable<List<Country>> = countriesRepo.getCountries()

}