package com.example.catslist.fragments.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catslist.db.RoomItem
import com.example.catslist.model.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
) : ViewModel() {
    public val data: MutableStateFlow<List<RoomItem>?> = MutableStateFlow(null)

    init {
        viewModelScope.launch(Dispatchers.IO) {

            val items = repository.loadData()

            items?.let {
                data.emit(it)
            }
        }
    }
}