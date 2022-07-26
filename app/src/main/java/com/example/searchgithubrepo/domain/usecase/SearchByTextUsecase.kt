package com.example.searchgithubrepo.domain.usecase

import com.example.searchgithubrepo.domain.repository.GithubRepository
import javax.inject.Inject

class SearchByTextUsecase @Inject constructor(private val repo: GithubRepository) {
    suspend operator fun invoke(query: String) = repo.search(query)
}
