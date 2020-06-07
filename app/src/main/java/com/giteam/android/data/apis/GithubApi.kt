package com.giteam.android.data.apis

import com.giteam.android.data.apis.responses.UsersResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {

    @GET("search/users")
    suspend fun getUsersWithName(@Query("q") query: String,
                                 @Query("page") page: Int,
                                 @Query("per_page") perPage: Int): UsersResponse

    companion object {
        const val BASE_URL = "https://api.github.com"
    }
}