package com.example.searchgithubrepo.domain.repository

import androidx.paging.PagingData
import com.example.searchgithubrepo.domain.model.RepoModel
import kotlinx.coroutines.flow.Flow

interface GithubRepository {
    suspend fun search(query: String): Flow<PagingData<RepoModel>>
}