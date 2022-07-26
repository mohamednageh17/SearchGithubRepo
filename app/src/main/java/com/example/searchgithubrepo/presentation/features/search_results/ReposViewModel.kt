package com.example.searchgithubrepo.presentation.features.search_results

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bumptech.glide.load.HttpException
import com.example.searchgithubrepo.domain.model.RepoModel
import com.example.searchgithubrepo.domain.usecase.SearchByTextUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ReposViewModel @Inject constructor(
    private val searchByTextUsecase: SearchByTextUsecase
) : ViewModel() {
    private val _reposFlow = MutableStateFlow<PagingData<RepoModel>>(PagingData.empty())
    val reposFlow: Flow<PagingData<RepoModel>>
        get() = _reposFlow

    suspend fun searchRepo(queryString: String) {
        try {
            searchByTextUsecase.invoke(queryString)
                .cachedIn(viewModelScope)
                .collect {
                    _reposFlow.emit(it)
                }
        } catch (exception: Exception) {
        } catch (exception: HttpException) {
        }
    }
}

