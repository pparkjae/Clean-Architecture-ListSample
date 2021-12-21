package com.listsample.data.repository

import androidx.paging.ExperimentalPagingApi
import com.listsample.data.api.BookApi
import com.listsample.data.exception.EmptyResultException
import com.listsample.data.exception.NetworkException
import com.listsample.data.mapper.BookEntityMapper
import com.listsample.data.model.BookResponse
import com.listsample.domain.entity.NetworkStatus
import com.listsample.domain.repository.BookRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ExperimentalPagingApi
class BookRepositoryImpl @Inject constructor(
    private val bookApi: BookApi
) : BookRepository {
    companion object {
        const val SIZE = 50
    }

    override fun getSearchBook(searchText: String, pageNum: Int) = flow {
        val response: BookResponse = bookApi.searchBook(searchText, pageNum, SIZE)

        if (response.metaData.totalCount != 0) {
            emit(NetworkStatus.Success(BookEntityMapper.mapperToBook(response)))
        } else {
            emit(NetworkStatus.Error(EmptyResultException()))
        }
    }.catch {
        emit(NetworkStatus.Error(NetworkException()))
    }.flowOn(Dispatchers.IO)
}