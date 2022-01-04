package com.listsample.domain.usecase

import com.listsample.domain.repository.BookRepository
import javax.inject.Inject

class BookUseCase @Inject constructor(private val bookRepository: BookRepository) {
    suspend fun getSearchBook(searchText: String, pageNum: Int) = bookRepository.getSearchBook(searchText, pageNum)
}