package com.giteam.android.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.lang.RuntimeException
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Suppress("UNCHECKED_CAST")
@Singleton
class ViewModelProviderFactory
@Inject constructor(
    private val viewModelMaps: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>)
    : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val creator = viewModelMaps[modelClass] ?:
                viewModelMaps.asIterable().firstOrNull {
                    modelClass.isAssignableFrom(it.key)
                }?.value ?: throw  IllegalArgumentException("Unknown ViewModel $modelClass")

        return try {
            creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e);
        }
    }
}