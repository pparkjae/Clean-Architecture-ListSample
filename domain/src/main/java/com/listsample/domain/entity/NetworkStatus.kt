package com.listsample.domain.entity

sealed class NetworkStatus<out T> {
    object Loading : NetworkStatus<Nothing>()
    data class Success<out T>(val data: T?) : NetworkStatus<T>()
    data class Error(val throwable: Throwable?) : NetworkStatus<Nothing>()
}