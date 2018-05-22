package com.cyalc.jakesrepos.data.models

sealed class NetworkState {
    class Success() : NetworkState()
    class Failure(val message: String) : NetworkState()
    class Loading : NetworkState()
}
