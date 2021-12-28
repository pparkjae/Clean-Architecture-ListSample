package com.listsample.data.di

import com.listsample.data.api.BookApi
import com.listsample.data.repository.BookRepositoryImpl
import com.listsample.domain.repository.BookRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideBookRepository(api: BookApi): BookRepository {
        return BookRepositoryImpl(api)
    }
}