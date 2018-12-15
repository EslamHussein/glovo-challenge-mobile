package com.glovo.glovo.map.domain

import com.glovo.glovo.base.usecase.UseCase
import com.glovo.glovo.map.data.dto.Country
import com.glovo.glovo.map.data.remote.repo.CountriesRemoteRepo
import io.reactivex.Observable

class GetCountriesUseCase(private val countriesRemoteRepo: CountriesRemoteRepo) :
    UseCase<Nothing, Observable<List<Country>>> {

    override fun execute(params: Nothing?): Observable<List<Country>> = countriesRemoteRepo.getCountries()

}