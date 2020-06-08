package com.giteam.android.di.modules

import com.giteam.android.data.apis.GithubApi
import com.giteam.android.data.repositories.UsersRepository
import com.giteam.android.data.repositories.UsersRepositoryImpl
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun providesUserRepository(githubApi: GithubApi): UsersRepository {
        return UsersRepositoryImpl(githubApi)
    }
}