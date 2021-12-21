package com.example.listsample.view.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.listsample.databinding.ItemBookBinding
import com.listsample.domain.entity.DocumentsData
import javax.inject.Inject

class BookAdapter @Inject constructor() :
    ListAdapter<DocumentsData, BookAdapter.ViewHolder>(TermsListDiffCallback) {
    var onClickDetail: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookAdapter.ViewHolder {
        return ViewHolder(
            ItemBookBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it, position)
        }
    }

    /**************************************************************************
     * view holder
     **************************************************************************/
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

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    object TermsListDiffCallback : DiffUtil.ItemCallback<DocumentsData>() {
        override fun areItemsTheSame(oldItem: DocumentsData, newItem: DocumentsData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: DocumentsData, newItem: DocumentsData): Boolean {
            return oldItem == newItem
        }
    }
}
