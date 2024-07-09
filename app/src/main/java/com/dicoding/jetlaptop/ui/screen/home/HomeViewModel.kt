package com.dicoding.jetlaptop.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.jetlaptop.data.LaptopRepository
import com.dicoding.jetlaptop.model.Laptop
import com.dicoding.jetlaptop.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: LaptopRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<List<Laptop>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Laptop>>>
        get() = _uiState

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun searchLaptop(newQuery: String) = viewModelScope.launch {
        _query.value = newQuery

        repository.searchLaptop(_query.value)
            .catch {
                _uiState.value = UiState.Error(it.message.toString())
            }
            .collect {
                _uiState.value = UiState.Success(it)
            }
    }

    fun updateLaptop(id: Int, newState: Boolean) = viewModelScope.launch {
        repository.updateLaptop(id, newState)
            .collect { isUpdated ->
                if (isUpdated) searchLaptop(_query.value)
            }
    }
}