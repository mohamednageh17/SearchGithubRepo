package com.example.searchgithubrepo.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bumptech.glide.load.HttpException
import com.example.searchgithubrepo.data.mapper.mapToDomain
import com.example.searchgithubrepo.data.remote.GithubApi
import com.example.searchgithubrepo.data.repository.GithubRepositoryImpl.Companion.NETWORK_PAGE_SIZE
import com.example.searchgithubrepo.domain.model.RepoModel
import java.io.IOException

private const val GITHUB_STARTING_PAGE_INDEX = 1

class GithubPagingSource(
    private val api: GithubApi,
    private val query: String
) : PagingSource<Int, RepoModel>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RepoModel> {
        val position = params.key ?: GITHUB_STARTING_PAGE_INDEX
        val apiQuery = query
        return try {
            val response = api.search(query = apiQuery, page = position, itemsPerPage = NETWORK_PAGE_SIZE)
            val repos = response.mapToDomain()
            val nextKey = if (repos.isEmpty()) {
                null
            } else {
                position + (params.loadSize / NETWORK_PAGE_SIZE)
            }
            LoadResult.Page(
                data = repos,
                prevKey = if (position == GITHUB_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, RepoModel>): Int? {
        return null
    }
}