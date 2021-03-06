package com.example.catslist.fragments.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catslist.db.RoomItem
import com.example.catslist.model.CATEGORY_1
import com.example.catslist.model.HomeRepository
import com.example.catslist.model.HomeRepositoryDataResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
) : ViewModel() {
    val data: MutableStateFlow<HomeRepositoryDataResponse?> = MutableStateFlow(null)

    private var workActive = false

    var activeCategory = CATEGORY_1

    init {
        loadData()
    }

    fun loadData() {
        if ((data.value == null || data.value?.data == null) && !workActive) {
            workActive = true

            viewModelScope.launch(Dispatchers.IO) {
                val response = repository.loadData()
                data.emit(response)
                workActive = false
            }
        }
    }
}