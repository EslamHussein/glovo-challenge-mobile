package com.glovo.glovo.selectlocation.domain.dto

import com.glovo.glovo.map.data.dto.City
import com.glovo.glovo.map.data.dto.Country

data class Locations(val counties: ArrayList<Country>, val cities: ArrayList<City>)