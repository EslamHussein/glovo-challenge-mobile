package com.glovo.glovo.selectlocation.domain

import com.glovo.glovo.base.usecase.UseCase
import com.glovo.glovo.map.data.CitiesRepo
import com.glovo.glovo.map.data.CountriesRepo
import com.glovo.glovo.map.data.dto.City
import com.glovo.glovo.map.data.dto.Country
import com.glovo.glovo.selectlocation.domain.dto.Locations
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

class GetCountriesCitiesUseCase(private val citiesRepo: CitiesRepo, private val countriesRepo: CountriesRepo) :
    UseCase<Nothing, Observable<Locations>> {
    override fun execute(params: Nothing?): Observable<Locations> {
        return Observable.zip(
            countriesRepo.getCountries(),
            citiesRepo.getCities(),
            BiFunction<List<Country>, List<City>, Locations> { countries, cities ->
                return@BiFunction Locations(ArrayList(countries), ArrayList(cities))
            })
    }
}