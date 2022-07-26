package com.example.searchgithubrepo.di

import com.example.searchgithubrepo.data.remote.GithubApi
import com.example.searchgithubrepo.data.repository.GithubRepositoryImpl
import com.example.searchgithubrepo.domain.repository.GithubRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {
    @Provides
    fun getRepositoryModule(api: GithubApi): GithubRepository {
        return GithubRepositoryImpl(api)
    }
}
