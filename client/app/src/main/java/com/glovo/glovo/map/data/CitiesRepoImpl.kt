package com.glovo.glovo.map.data

import com.glovo.glovo.map.data.dto.City
import com.glovo.glovo.map.data.remote.repo.CitiesRemoteRepo
import io.reactivex.Observable

class CitiesRepoImpl(private val remoteRepo: CitiesRemoteRepo) : CitiesRepo {
    override fun getCities(): Observable<List<City>> {
        return remoteRepo.getCities()
    }

    override fun getCityDetails(cityCode: String): Observable<City> {
        return remoteRepo.getCityDetails(cityCode)
    }
}