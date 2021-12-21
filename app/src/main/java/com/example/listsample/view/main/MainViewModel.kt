package com.example.listsample.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    private var _bookData = MutableLiveData(emptyList<DocumentsData>())
    val bookData: LiveData<List<DocumentsData>> = _bookData

    private val _resultState =
        MutableStateFlow<NetworkStatus<List<DocumentsData>>>(NetworkStatus.Success(null))
    val resultState: StateFlow<NetworkStatus<List<DocumentsData>>> = _resultState

    suspend fun requestBook(pageNum: Int, pagingType: PagingType) =
        bookUseCase.getSearchBook(searchText.value, pageNum).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = NetworkStatus.Loading
        ).collect {
            when (it) {
                is NetworkStatus.Success -> {
                    it.data?.run {
                        when (pagingType) {
                            PagingType.INITIALIZE -> {
                                _bookData.value = documentList
                            }

                            PagingType.APPEND -> {
                                val result = _bookData.value?.toMutableList().also { data ->
                                    data?.addAll(documentList)
                                }
                                _bookData.value = result
                            }
                        }
                    }

                    _resultState.value = NetworkStatus.Success(_bookData.value)
                }

                is NetworkStatus.Loading -> {
                    _resultState.value = NetworkStatus.Loading
                }

                is NetworkStatus.Error -> {
                    _resultState.value = NetworkStatus.Error(it.throwable)
                }
            }

        }
}