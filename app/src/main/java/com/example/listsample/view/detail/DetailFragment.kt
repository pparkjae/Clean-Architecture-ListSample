package com.example.listsample.view.detail

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
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

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        detailFragmentBinding = FragmentDetailBinding.inflate(inflater, container, false).apply {
            vm = viewModel
            documentsData = viewModel.bookData[args.position]
            lifecycleOwner = this@DetailFragment

            executePendingBindings()
        }

        detailFragmentBinding.detailBookLike.setOnClickListener {
            viewModel.bookData[args.position].isLike = viewModel.bookData[args.position].isLike.not()

            detailFragmentBinding.documentsData = viewModel.bookData[args.position]
            detailFragmentBinding.executePendingBindings()
        }

        return detailFragmentBinding.root
    }
}