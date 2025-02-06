package com.example.shoebox.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoebox.data.shoeModel.Shoe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ShoeViewModel(private val repository: ShoeRepository) : ViewModel() {
    private val _shoes = MutableStateFlow<List<Shoe>>(emptyList())
    val shoes: StateFlow<List<Shoe>> = _shoes.asStateFlow()

    init {
        viewModelScope.launch {
            fetchShoes()
        }
    }
    private suspend fun fetchShoes() {
        repository.fetchShoes()
        repository.getShoes().collect { shoes ->
            _shoes.value = shoes
        }
    }
}