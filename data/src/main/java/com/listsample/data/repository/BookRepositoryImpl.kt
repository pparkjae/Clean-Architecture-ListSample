package com.listsample.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.listsample.data.paging.BookPagingSource
import com.listsample.data.remote.api.BookApi
import com.listsample.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
class BookRepositoryImpl @Inject constructor(
    private val bookApi: BookApi
) : BookRepository {
    companion object {
        const val SIZE = 50
    }

    override suspend fun <T> getSearchBook(searchText: String): Flow<T> {
        return Pager(
            config = PagingConfig(pageSize = SIZE, initialLoadSize = SIZE),
            pagingSourceFactory = {
                BookPagingSource(searchText, bookApi)
            }
        ).flow as Flow<T>
    }
}