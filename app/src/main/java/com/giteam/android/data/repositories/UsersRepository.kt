package com.giteam.android.data.repositories

import com.giteam.android.data.apis.GithubApi
import com.giteam.android.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface UsersRepository {
    fun getUsersWithUsername(name: String, page: Int, perPage: Int = 25) : Flow<List<User>>
}

@ExperimentalCoroutinesApi
class UsersRepositoryImpl
@Inject constructor(private var githubApi: GithubApi) : UsersRepository {
    override fun getUsersWithUsername(name: String, page: Int, perPage: Int) : Flow<List<User>> {
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