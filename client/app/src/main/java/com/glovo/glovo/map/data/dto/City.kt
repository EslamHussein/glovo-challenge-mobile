package com.glovo.glovo.map.data.dto

import com.google.gson.annotations.SerializedName

data class City(
    @SerializedName("country_code") val countryCode: String,
    val code: String,
    val name: String, @SerializedName("working_area") val workingArea: List<String>,
    val currency: String? = null,
    val enabled: Boolean? = true, @SerializedName("time_zone") val timeZone: String? = null
) {
    override fun toString(): String {
        return name
    }
}