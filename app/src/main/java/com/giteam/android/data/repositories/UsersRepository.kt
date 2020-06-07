package com.giteam.android.data.repositories

import com.giteam.android.data.apis.GithubApi
import com.giteam.android.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@ExperimentalCoroutinesApi
class UsersRepository
@Inject constructor(private var githubApi: GithubApi) {
    fun getUsersWithUsername(name: String, page: Int, perPage: Int = 25) : Flow<List<User>> {
         return flow {
             kotlinx.coroutines.delay(500)

             if(name.isEmpty()) {
                 emit(listOf())
                 return@flow
             }

             val response = githubApi.getUsersWithName(name, page, perPage)
             emit(response.items)
         }.flowOn(Dispatchers.IO)
    }
}