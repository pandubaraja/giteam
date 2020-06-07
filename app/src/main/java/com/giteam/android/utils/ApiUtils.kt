package com.giteam.android.utils

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import retrofit2.HttpException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException

@ExperimentalCoroutinesApi
fun <T> Flow<T>.handleErrors(
    onConnectionError: ((e: Throwable) -> Unit)? = null,
    onRateLimitError: ((e: Throwable) -> Unit)? = null,
    onError: ((e: Throwable) -> Unit)? = null): Flow<T> =
    catch { e ->
        when(e){
            is UnknownHostException,
            is SocketTimeoutException -> {
                onConnectionError?.invoke(e)
            }
            is HttpException -> {
                if(e.code() == HttpURLConnection.HTTP_FORBIDDEN) {
                    onRateLimitError?.invoke(e)
                }
                else {
                    onError?.invoke(e)
                }
            }
            else -> {
                onError?.invoke(e)
            }
        }
    }