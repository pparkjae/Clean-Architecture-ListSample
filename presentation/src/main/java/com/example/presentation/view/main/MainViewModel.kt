package com.example.presentation.view.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.listsample.domain.entity.DocumentsData
import com.listsample.domain.usecase.BookUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val bookUseCase: BookUseCase) : ViewModel() {
    val searchText = MutableStateFlow("")

    private val _searchItemPrivate = MutableStateFlow<PagingData<DocumentsData>>(PagingData.empty())
    val searchItem: MutableStateFlow<PagingData<DocumentsData>> get() = _searchItemPrivate

    suspend fun requestSearchBook(searchText: String) {
        bookUseCase.getSearchBook<PagingData<DocumentsData>>(searchText)
            .cachedIn(viewModelScope).collect {
                _searchItemPrivate.value = it
            }
    }
}