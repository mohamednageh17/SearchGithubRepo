package com.example.searchgithubrepo.data.remote

import com.example.searchgithubrepo.data.model.GithubRepoResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface GithubApi {
    @GET("search/repositories")
    suspend fun search(
        @Header("accept") header: String = "application/vnd.github+json",
        @Query("q") query: String,
        @Query("sort") sort: String = "stars",
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): GithubRepoResponse
}

