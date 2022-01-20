package com.example.presentation.view.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.presentation.databinding.ItemStatusAdapterBinding
import javax.inject.Inject

class StatusAdapter @Inject constructor(
    private val retry: () -> Unit
) : LoadStateAdapter<StatusAdapter.NetworkStateItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) =

        NetworkStateItemViewHolder(
            ItemStatusAdapterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ) {
            retry.invoke()
        }

    override fun onBindViewHolder(holder: NetworkStateItemViewHolder, loadState: LoadState) =
        holder.bind(loadState)

    inner class NetworkStateItemViewHolder(
        private val binding: ItemStatusAdapterBinding,
        private val retryCallback: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                binding.statusAdapterRetry.setOnClickListener {
                    retryCallback()
                }
            }
        }
    }
}