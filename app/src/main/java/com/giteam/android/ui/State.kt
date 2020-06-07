package com.giteam.android.ui

sealed class State<T>(val data: T){
    class Loading<T>(data: T): State<T>(data)
    class Success<T>(data: T): State<T>(data)
    class Error<T>(data: T, val messageResId: Int? = null): State<T>(data)
    class Idle<T>(data: T): State<T>(data)
}