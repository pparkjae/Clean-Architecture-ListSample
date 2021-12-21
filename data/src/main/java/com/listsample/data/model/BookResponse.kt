package com.listsample.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookResponse(
    @SerialName("meta") val metaData: MetaDataResponse,
    @SerialName("documents") val documentList: List<DocumentsDataResponse>
)

@Serializable
data class MetaDataResponse(
    @SerialName("is_end") val isEnd: Boolean,
    @SerialName("pageable_count") val pageCount: Int,
    @SerialName("total_count") val totalCount: Int,
)

@Serializable
data class DocumentsDataResponse(
    @SerialName("title") val title: String,
    @SerialName("contents") val contents: String,
    @SerialName("url") val url: String,
    @SerialName("isbn") val isbn: String,
    @SerialName("datetime") val datetime: String,
    @SerialName("authors") val authorList: List<String>,
    @SerialName("publisher") val publisher: String,
    @SerialName("translators") val translatorList: List<String>,
    @SerialName("price") val price: Int,
    @SerialName("sale_price") val salePrice: Int,
    @SerialName("thumbnail") val thumbnailUrl: String,
    @SerialName("status") val status: String,
) : java.io.Serializable
