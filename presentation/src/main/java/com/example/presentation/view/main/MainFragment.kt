package com.example.presentation.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.example.presentation.R
import com.example.presentation.base.BaseNavigationFragment
import com.example.presentation.databinding.FragmentMainBinding
import com.example.presentation.view.main.adapter.BookPagingAdapter
import com.example.presentation.view.main.adapter.StatusAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : BaseNavigationFragment(R.layout.fragment_main) {
    private lateinit var mainFragmentBinding: FragmentMainBinding
    private val viewModel: MainViewModel by activityViewModels()

    @Inject
    lateinit var pagingAdapter: BookPagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mainFragmentBinding = FragmentMainBinding.inflate(inflater, container, false).apply {
            vm = viewModel
            lifecycleOwner = this@MainFragment
        }
        return mainFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainFragmentBinding.recyclerView.adapter = pagingAdapter.run {
            addLoadStateListener { states ->
                if (states.source.refresh is LoadState.Error) {
                    Toast.makeText(requireContext(), (states.source.refresh as LoadState.Error).error.message, Toast.LENGTH_SHORT).show()
                }
            }

            withLoadStateFooter(StatusAdapter(pagingAdapter::retry))
        }

        mainFragmentBinding.swipeRefreshLayout.setOnRefreshListener {
            pagingAdapter.refresh()
            mainFragmentBinding.swipeRefreshLayout.isRefreshing = false
        }

        mainFragmentBinding.searchButton.setOnClickListener {
            if (viewModel.searchText.value.isEmpty()) {
                Toast.makeText(requireContext(), "검색어 입력이 필요합니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                viewModel.requestSearchBook(viewModel.searchText.value)
            }
        }

        pagingAdapter.onClickDetail = {
            navigate(R.id.actionDetailFragment, bundleOf("position" to it))
        }

        observe()
    }

    private fun observe() {
        lifecycleScope.launch {

            viewModel.searchItem.collectLatest {
                pagingAdapter.submitData(it)
            }
        }
    }
}