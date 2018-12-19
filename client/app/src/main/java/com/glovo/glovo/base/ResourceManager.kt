package com.glovo.glovo.base

import android.content.Context
import androidx.annotation.StringRes

class ResourceManager constructor(private val context: Context) {

    fun getString(@StringRes id: Int): String = context.getString(id)

}