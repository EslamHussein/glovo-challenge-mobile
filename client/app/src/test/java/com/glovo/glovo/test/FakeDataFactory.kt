package com.glovo.glovo.test

import com.glovo.glovo.map.data.dto.City
import com.glovo.glovo.map.data.dto.Country

object FakeDataFactory {


    fun createListOfCities(): List<City> {

        val mutableData = mutableListOf<City>()
        repeat(10)
        {
            mutableData.add(createCity())
        }
        return mutableData

    }


    fun createListOfCountries(): List<Country> {
        val mutableData = mutableListOf<Country>()
        repeat(10)
        {
            mutableData.add(createCountry())
        }
        return mutableData

    }


    private fun createCity(): City {

        return City(
            DataFactory.randomString(), DataFactory.randomString()
            , DataFactory.randomString(), listOf(DataFactory.randomString(), DataFactory.randomString())
        )
    }


    private fun createCountry(): Country {

        return Country(DataFactory.randomString(), DataFactory.randomString())
    }


}
