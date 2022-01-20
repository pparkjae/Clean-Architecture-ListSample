package com.listsample.domain.repository

import kotlinx.coroutines.flow.Flow

interface BookRepository {
    suspend fun <T> getSearchBook(searchText: String) : Flow<T>
}