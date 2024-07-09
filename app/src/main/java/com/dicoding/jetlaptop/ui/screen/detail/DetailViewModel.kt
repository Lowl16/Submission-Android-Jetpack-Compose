package com.dicoding.jetlaptop.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.jetlaptop.data.LaptopRepository
import com.dicoding.jetlaptop.model.Laptop
import com.dicoding.jetlaptop.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: LaptopRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<Laptop>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Laptop>>
        get() = _uiState

    fun getLaptopById(id: Int) = viewModelScope.launch {
        _uiState.value = UiState.Loading
        _uiState.value = UiState.Success(repository.getLaptopById(id))
    }

    fun updateLaptop(id: Int, newState: Boolean) = viewModelScope.launch {
        repository.updateLaptop(id, !newState)
            .collect { isUpdated ->
                if (isUpdated) getLaptopById(id)
            }
    }
}