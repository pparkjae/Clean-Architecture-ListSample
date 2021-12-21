package com.listsample.domain.entity

data class Book(
    val metaData: MetaData,
    val documentList: List<DocumentsData>
)

data class MetaData(
    val isEnd: Boolean,
    val pageCount: Int,
    val totalCount: Int,
)

data class DocumentsData(
    val title: String,
    val contents: String,
    val url: String,
    val isbn: String,
    val datetime: String,
    val authorList: List<String>,
    val publisher: String,
    val translatorList: List<String>,
    val price: Int,
    val salePrice: Int,
    val thumbnailUrl: String,
    val status: String,
    var isLike: Boolean = false
)
