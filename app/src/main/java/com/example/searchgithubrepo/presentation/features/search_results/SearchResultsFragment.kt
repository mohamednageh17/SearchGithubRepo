package com.example.searchgithubrepo.presentation.features.search_results

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.View.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.example.searchgithubrepo.R
import com.example.searchgithubrepo.databinding.FragmentSearchResultsBinding
import com.example.searchgithubrepo.domain.model.RepoModel
import com.example.searchgithubrepo.presentation.Utils
import com.example.searchgithubrepo.presentation.features.search_results.adapter.ReposAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SearchResultsFragment : Fragment() {

    private lateinit var binding: FragmentSearchResultsBinding

    private val viewModel by lazy { ViewModelProvider(requireActivity())[ReposViewModel::class.java] }

    private val adapter by lazy {
        ReposAdapter {
            val action = SearchResultsFragmentDirections.viewDetails(it)
            findNavController().navigate(action)
        }
    }

    private var searchView: SearchView? = null
    private var queryTextListener: SearchView.OnQueryTextListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSearchResultsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        @Suppress("DEPRECATION")
        setHasOptionsMenu(true)
        initRecyclerView()
        handleIdleState()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.reposRV.adapter = null
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val searchItem = menu.findItem(R.id.search)
        val searchManager = requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager

        if (searchItem != null) {
            searchView = searchItem.actionView as SearchView
        }
        if (searchView != null) {
            searchView!!.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
            queryTextListener = object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String): Boolean {
                    if (newText.isEmpty()) {
                        handleIdleState()
                    }
                    return true
                }

                override fun onQueryTextSubmit(query: String): Boolean {
                    onSubmitQuery(query)
                    return true
                }
            }
            searchView!!.setOnQueryTextListener(queryTextListener)
        }
    }

    private fun initRecyclerView() {
        binding.reposRV.adapter = adapter
    }

    private fun observeUI() {
        lifecycleScope.launch {
            viewModel.reposFlow.collectLatest {
                handleSuccessState(it)
            }
        }
    }

    private fun observeLoadState() {
        lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest { loadState ->
                binding.loadingProgress.visibility = when (loadState.refresh) {
                    is LoadState.Loading -> VISIBLE
                    is LoadState.NotLoading -> INVISIBLE
                    is LoadState.Error -> {
                        val errorMessage = (loadState.refresh as LoadState.Error).error.message
                        handleErrorState(errorMessage.toString())
                        INVISIBLE
                    }
                }
            }
        }
    }

    private fun showRV() {
        binding.reposRV.visibility = VISIBLE
    }

    private fun hideRV() {
        binding.reposRV.visibility = GONE
    }

    private fun showRetryBtn() {
        binding.retryButton.visibility = VISIBLE
    }

    private fun hideRetryBtn() {
        binding.retryButton.visibility = GONE
    }

    private fun showMessageTV(message: String) {
        with(binding) {
            messageTV.visibility = VISIBLE
            messageTV.text = message
        }
    }

    private fun hideMessageTV() {
        binding.messageTV.visibility = GONE
    }

    private fun showProgress() {
        binding.loadingProgress.visibility = VISIBLE
    }

    private fun hideProgress() {
        binding.loadingProgress.visibility = GONE
    }

    private fun handleIdleState() {
        hideProgress()
        hideRV()
        hideRetryBtn()
        showMessageTV("Enter key word to start searching..")
    }

    private fun handleStartSearchingState() {
        showProgress()
        hideMessageTV()
        hideRetryBtn()
        adapter.submitData(lifecycle, PagingData.empty())
    }

    private suspend fun handleSuccessState(pagingData: PagingData<RepoModel>) {
        hideProgress()
        hideMessageTV()
        showRV()
        hideRetryBtn()
        adapter.submitData(pagingData)
    }

    private fun handleErrorState(error: String) {
        if (!Utils.isNetworkAvailable(requireContext())) {
            showMessageTV(getString(R.string.network_not_available))
        } else {
            showMessageTV(error)
        }
        showRetryBtn()
        binding.retryButton.setOnClickListener {
            showProgress()
            adapter.retry()
            observeUI()
        }
    }

    private fun onSubmitQuery(query: String) {
        handleStartSearchingState()
        lifecycleScope.launch {
            viewModel.searchRepo(query)
        }
        observeUI()
        observeLoadState()
    }
}