package com.glovo.glovo.map.data.dto

data class Country(val code: String, val name: String) {

    override fun toString(): String {
        return name
    }
}