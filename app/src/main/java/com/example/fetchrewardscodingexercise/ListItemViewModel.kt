package com.example.fetchrewardscodingexercise

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ListItemViewModel : ViewModel() {

    private val repository = ListItemRepository()

    private val _listItems = MutableLiveData<Map<Int, List<ListItem>>>()
    val listItems: LiveData<Map<Int, List<ListItem>>> get() = _listItems

    fun fetchListItems() {
        viewModelScope.launch {
            try {
                // Fetch data
                val items = repository.getListItems()
                _listItems.value = items
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}