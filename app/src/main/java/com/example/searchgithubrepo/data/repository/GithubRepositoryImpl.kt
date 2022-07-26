package com.example.searchgithubrepo.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.searchgithubrepo.data.paging.GithubPagingSource
import com.example.searchgithubrepo.data.remote.GithubApi
import com.example.searchgithubrepo.domain.model.RepoModel
import com.example.searchgithubrepo.domain.repository.GithubRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(private val api: GithubApi) : GithubRepository {
    override suspend fun search(query: String): Flow<PagingData<RepoModel>> {
        return Pager(
            config = PagingConfig(
                initialLoadSize = NETWORK_PAGE_SIZE,
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { GithubPagingSource(api, query) }
        ).flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 10
    }
}