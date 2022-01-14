package com.example.presentation.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.presentation.R
import com.example.presentation.base.BaseNavigationFragment
import com.example.presentation.databinding.FragmentMainBinding
import com.example.presentation.view.main.adapter.BookAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : BaseNavigationFragment(R.layout.fragment_main) {
    lateinit var mainFragmentBinding: FragmentMainBinding
    private val viewModel: MainViewModel by activityViewModels()

    private var pageNum = 1

    @Inject
    lateinit var bookAdapter: BookAdapter

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
        mainFragmentBinding.recyclerView.adapter = bookAdapter

        mainFragmentBinding.searchButton.setOnClickListener {
            lifecycleScope.launch {
                pageNum = 1
                viewModel.requestBook(pageNum, PagingType.INITIALIZE)
            }
        }

        mainFragmentBinding.recyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager)
                    .findLastCompletelyVisibleItemPosition()
                val totalCount = recyclerView.adapter!!.itemCount - 1

                if (recyclerView.canScrollVertically(1).not() && lastVisibleItemPosition == totalCount) {
                    lifecycleScope.launch {
                        viewModel.requestBook(pageNum++, PagingType.APPEND)
                    }
                }
            }
        })

        bookAdapter.onClickDetail = {
            navigate(R.id.actionDetailFragment, bundleOf("position" to it))
        }
    }
}