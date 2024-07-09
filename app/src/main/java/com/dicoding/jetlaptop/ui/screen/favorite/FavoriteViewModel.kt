package com.dicoding.jetlaptop.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.jetlaptop.data.LaptopRepository
import com.dicoding.jetlaptop.model.Laptop
import com.dicoding.jetlaptop.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: LaptopRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<List<Laptop>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Laptop>>>
        get() = _uiState

    fun getFavoriteLaptop() = viewModelScope.launch {
        repository.getFavoriteLaptop()
            .catch {
                _uiState.value = UiState.Error(it.message.toString())
            }
            .collect {
                _uiState.value = UiState.Success(it)
            }
    }

    fun updateLaptop(id: Int, newState: Boolean) {
        repository.updateLaptop(id, newState)

        getFavoriteLaptop()
    }
}