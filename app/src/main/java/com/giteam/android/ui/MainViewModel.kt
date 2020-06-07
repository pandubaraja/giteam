package com.giteam.android.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giteam.android.R
import com.giteam.android.data.repositories.UsersRepository
import com.giteam.android.ui.adapter.BaseViewItem
import com.giteam.android.ui.mappers.UserMapper
import com.giteam.android.ui.viewholders.*
import com.giteam.android.utils.handleErrors
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
class MainViewModel
@Inject constructor(private val usersRepository: UsersRepository): ViewModel(){

    private val _usersLiveData = MutableLiveData<State<List<BaseViewItem>>>()
    private var job: Job? = null
    private var page = 1
    private var perPage = 20
    private var bufferPerPage = 1
    private var perPageBufferedNextPage = perPage + bufferPerPage
    private var currentName = ""
    private var _newUserFirstPosition = 0

    val usersLiveData: LiveData<State<List<BaseViewItem>>>
        get() = _usersLiveData

    val newUserFirstPosition
        get() = _newUserFirstPosition

    init {
        showIdleState()
    }

    fun fetchUsersWithName(name: String){
        job?.cancel()
        page = 1
        currentName = name

        if(name.isEmpty()){
            showIdleState()
            return
        }

        showLoadingState(reset = true)

        job = viewModelScope.launch {
            usersRepository.getUsersWithUsername(name, page, perPageBufferedNextPage)
                .handleErrors(
                    onConnectionError = {
                        showErrorState(R.string.error_connection)
                    }, onRateLimitError = {
                        showErrorState(R.string.error_github_api_rate_limit)
                    }
                )
                .map { UserMapper.mapToUserItem(it) }
                .collect {
                    if(it.isEmpty()) showEmptyState()
                    else showSuccessState(it)
                }
        }
    }

    fun loadMoreUsers(){
        if(currentName.isEmpty()){
            return
        }

        val isThereAnyUsersAlready = _usersLiveData.value?.data?.firstOrNull { it is UserItem } != null
        if(isThereAnyUsersAlready.not()) showLoadingState(reset = true)
        else showLoadingState()

        job?.cancel()
        job = viewModelScope.launch {
            usersRepository.getUsersWithUsername(currentName, page + 1, perPageBufferedNextPage)
                .handleErrors(
                    onConnectionError = {
                        showErrorState(R.string.error_connection)
                    }, onRateLimitError = {
                        showErrorState(R.string.error_github_api_rate_limit)
                    }
                )
                .map { UserMapper.mapToUserItem(it) }
                .collect {
                    page++
                    showSuccessState(it)
                }
        }
    }

    private fun appendNewItems(oldItems: List<BaseViewItem>, newItems: List<BaseViewItem>): List<BaseViewItem> {
        return mutableListOf<BaseViewItem>().apply {
            addAll(oldItems)
            addAll(newItems)
        }
    }

    private fun showEmptyState(){
        _usersLiveData.postValue(State.Error(emptyItems()))
    }

    private fun showSuccessState(items: List<BaseViewItem> ){
        val currentItems = _usersLiveData.value?.data?.toMutableList() ?: mutableListOf()
        val newItems = items.toMutableList()

        if(items.size <= perPageBufferedNextPage - bufferPerPage) {
            //No More Item To Load
            val isNoMoreDataToLoad = currentItems.firstOrNull { it is NoMoreDataItem } != null
            if(isNoMoreDataToLoad.not()) newItems.addAll(noMoreItems())
        }
        else {
            newItems.removeAt(newItems.size - 1)
            newItems.addAll(loadingItems())
        }

        currentItems.firstOrNull { it is ErrorStateItem }?.let {
            currentItems.remove(it)
        }

        currentItems.firstOrNull { it is LoadingStateItem }?.also {
            currentItems.remove(it)
        }

        if(currentItems.isNotEmpty() && newItems.isNotEmpty()) {
            _newUserFirstPosition = (currentItems.size - 1) + (newItems.size / 4)
        }

        _usersLiveData.postValue(State.Success(appendNewItems(currentItems, newItems)))
    }

    private fun showErrorState(messageResId: Int){
        val currentItems = _usersLiveData.value?.data?.toMutableList() ?: mutableListOf()
        currentItems.firstOrNull { it is LoadingStateItem }?.let {
            currentItems.remove(it)
        }

        val isError = currentItems.firstOrNull { it is ErrorStateItem } != null

        if(isError.not()) {
            _usersLiveData.postValue(
                State.Error(
                    messageResId = messageResId, data = appendNewItems(currentItems, errorItems(messageResId))
                )
            )
        }
    }

    private fun showLoadingState(reset: Boolean = false){
        val currentItems = _usersLiveData.value?.data?.toMutableList() ?: mutableListOf()
        currentItems.firstOrNull { it is NoMoreDataItem }?.let {
            currentItems.remove(it)
        }

        currentItems.firstOrNull { it is ErrorStateItem }?.let {
            currentItems.remove(it)
        }

        //FORCE RELOAD REMOVE USERS
        if(reset) {
            _usersLiveData.postValue(State.Loading(loadingItems()))
            return
        }

        val isLoadingMore = currentItems.firstOrNull { it is LoadingStateItem } != null
        if(isLoadingMore.not()) {
            _usersLiveData.postValue(
                State.Loading(appendNewItems(currentItems, loadingItems()))
            )
        }
    }

    private fun showIdleState(){
        _usersLiveData.postValue(State.Idle(idleItems()))
    }

    private fun noMoreItems() = listOf(NoMoreDataItem())
    private fun errorItems(messageResId: Int? = null) = listOf(ErrorStateItem(messageResId))
    private fun loadingItems() = listOf(LoadingStateItem())
    private fun idleItems() = listOf(IdleStateItem())
    private fun emptyItems() = listOf(EmptyStateItem())
}