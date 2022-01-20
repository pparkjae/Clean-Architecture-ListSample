package com.example.presentation.view.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.presentation.databinding.ItemBookBinding
import com.listsample.domain.entity.DocumentsData
import javax.inject.Inject

class BookPagingAdapter @Inject constructor() :
    PagingDataAdapter<DocumentsData, BookPagingAdapter.ViewHolder>(bookDiffCallback) {
    var onClickDetail: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemBookBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val documentsData = getItem(position)
        if (documentsData != null) {
            holder.bind(documentsData, position)
        }
    }

    inner class ViewHolder(private val binding: ItemBookBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(documentsData: DocumentsData, position: Int) {
            binding.apply {
                this.documentsData = documentsData

                root.setOnClickListener {
                    onClickDetail?.invoke(position)
                }
                executePendingBindings()
            }
        }
    }

    companion object {
        private val bookDiffCallback = object : DiffUtil.ItemCallback<DocumentsData>() {
            override fun areItemsTheSame(oldItem: DocumentsData, newItem: DocumentsData): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: DocumentsData,
                newItem: DocumentsData
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}