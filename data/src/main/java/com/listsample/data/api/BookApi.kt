package com.listsample.data.api

import com.listsample.data.model.BookResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface BookApi {
    /**
     * 책 검색
     */
    @GET("v3/search/book")
    suspend fun searchBook(
        @Query("query") searchValue: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): BookResponse
}