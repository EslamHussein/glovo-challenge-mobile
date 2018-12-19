package com.glovo.glovo.test

import java.util.*

object DataFactory {

    fun randomString(): String = UUID.randomUUID().toString()


    fun randomInt(): Int {
        return (0..1000).shuffled().first()
    }
}