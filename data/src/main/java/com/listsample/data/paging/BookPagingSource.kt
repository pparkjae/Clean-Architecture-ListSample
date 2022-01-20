package com.listsample.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.listsample.data.exception.NetworkException
import com.listsample.data.mapper.BookEntityMapper
import com.listsample.data.remote.api.BookApi
import com.listsample.domain.entity.DocumentsData

class BookPagingSource(
    private val searchText: String,
    private val bookApi: BookApi,
) : PagingSource<Int, DocumentsData>() {

    override fun getRefreshKey(state: PagingState<Int, DocumentsData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DocumentsData> =
        try {
            val pageNumber = params.key ?: DEFAULT_PAGE_NUMBER
            val data = BookEntityMapper.mapperToBook(
                bookApi.searchBook(
                    searchText,
                    pageNumber,
                    params.loadSize
                )
            )

            LoadResult.Page(
                data = data.documentList,
                prevKey = null,
                nextKey = if (data.metaData.isEnd) null else pageNumber + 1
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(NetworkException())
        }

    companion object {
        private const val DEFAULT_PAGE_NUMBER = 1
    }
}