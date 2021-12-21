package com.example.listsample.view.detail

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.example.listsample.R
import com.example.listsample.base.BaseNavigationFragment
import com.example.listsample.databinding.FragmentDetailBinding
import com.example.listsample.view.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseNavigationFragment(R.layout.fragment_detail) {
    lateinit var detailFragmentBinding: FragmentDetailBinding
    private val args: DetailFragmentArgs by navArgs()

    private val mainViewModel: MainViewModel by navGraphViewModels(R.id.mainFragment) {
        defaultViewModelProviderFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        detailFragmentBinding = FragmentDetailBinding.inflate(inflater, container, false).apply {
            vm = mainViewModel
            documentsData = mainViewModel.bookData.value?.get(args.position)
            lifecycleOwner = this@DetailFragment

            executePendingBindings()
        }

        detailFragmentBinding.detailBookLike.setOnClickListener {
            val isLike = mainViewModel.bookData.value?.get(args.position)?.isLike!!

            if (isLike) {
                it.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.gray))
            } else {
                it.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.purple_700))
            }

            mainViewModel.bookData.value?.get(args.position)?.isLike = !isLike
        }

        return detailFragmentBinding.root
    }
}