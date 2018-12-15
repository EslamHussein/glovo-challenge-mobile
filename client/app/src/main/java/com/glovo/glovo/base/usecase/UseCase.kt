package com.glovo.glovo.base.usecase

interface UseCase<in P, R> {

    fun execute(params: P? = null): R

}