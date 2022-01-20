package com.listsample.domain.usecase

import com.listsample.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BookUseCase @Inject constructor(private val bookRepository: BookRepository) {
    suspend fun <T> getSearchBook(searchText: String): Flow<T> =
        bookRepository.getSearchBook(searchText)
}