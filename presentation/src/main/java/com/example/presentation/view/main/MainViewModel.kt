package com.example.presentation.view.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.listsample.domain.entity.DocumentsData
import com.listsample.domain.entity.NetworkStatus
import com.listsample.domain.usecase.BookUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val bookUseCase: BookUseCase) : ViewModel() {
    val searchText = MutableStateFlow("")
    val bookData = mutableListOf<DocumentsData>()

    private val resultStatePrivate =
        MutableStateFlow<NetworkStatus<List<DocumentsData>>>(NetworkStatus.Success(null))
    val resultState: StateFlow<NetworkStatus<List<DocumentsData>>> get() = resultStatePrivate

    suspend fun requestBook(pageNum: Int, pagingType: PagingType) =
        bookUseCase.getSearchBook(searchText.value, pageNum).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = NetworkStatus.Loading
        ).collect {
            when (it) {
                is NetworkStatus.Success -> {
                    resultStatePrivate.value = NetworkStatus.Success(it.data?.run {
                        when (pagingType) {
                            PagingType.INITIALIZE -> {
                                bookData.also { data ->
                                    data.clear()
                                    data.addAll(documentList)
                                }
                            }

                            PagingType.APPEND -> {
                                bookData.also { data ->
                                    data.addAll(documentList)
                                }
                            }
                        }
                    })
                }

                is NetworkStatus.Loading -> {
                    resultStatePrivate.value = NetworkStatus.Loading
                }

                is NetworkStatus.Error -> {
                    resultStatePrivate.value = NetworkStatus.Error(it.throwable)
                }
            }

        }
}