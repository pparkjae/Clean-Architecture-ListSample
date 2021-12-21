package com.listsample.data.mapper

import com.listsample.data.model.BookResponse
import com.listsample.domain.entity.Book
import com.listsample.domain.entity.DocumentsData
import com.listsample.domain.entity.MetaData

object BookEntityMapper {
    fun mapperToBook(bookResponse: BookResponse): Book {
        return Book(
            MetaData(
                bookResponse.metaData.isEnd,
                bookResponse.metaData.totalCount,
                bookResponse.metaData.pageCount
            ),
            bookResponse.documentList.map {
                DocumentsData(
                    it.title,
                    it.contents,
                    it.url,
                    it.isbn,
                    it.datetime,
                    it.authorList,
                    it.publisher,
                    it.translatorList,
                    it.price,
                    it.salePrice,
                    it.thumbnailUrl,
                    it.status
                )
            }
        )
    }
}