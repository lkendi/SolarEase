package com.app.solarease.common

/**
 * A sealed class that represents the state of a resource.
 *
 * This class encapsulates a resource that can be in one of three states:
 * Success, Error, or Loading.
 *
 */
sealed class Resource<T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Error<T>(val message: String) : Resource<T>()
    class Loading<T> : Resource<T>()
}
