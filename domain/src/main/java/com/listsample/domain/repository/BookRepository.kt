package com.listsample.domain.repository

import com.listsample.domain.entity.Book
import com.listsample.domain.entity.NetworkStatus
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    fun getSearchBook(searchText: String, pageNum: Int) : Flow<NetworkStatus<Book>>
}