package com.mohammadosman.unittest.util

sealed class Resource<out T> {

    object Loading : Resource<Nothing>()

    data class Success<T>(val data: T? = null, val msg: String) : Resource<T>()

    data class Error(val error: String?) : Resource<Nothing>()
}