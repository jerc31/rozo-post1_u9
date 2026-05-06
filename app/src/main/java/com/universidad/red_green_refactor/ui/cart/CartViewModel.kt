package com.universidad.red_green_refactor.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.universidad.red_green_refactor.domain.repository.CartRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CartViewModel(
    private val repository: CartRepository,
    private val analytics: AnalyticsService
) : ViewModel() {
    private val _uiState = MutableStateFlow<CartUiState>(CartUiState.Loading)
    val uiState: StateFlow<CartUiState> = _uiState.asStateFlow()

    fun loadCart() {
        viewModelScope.launch {
            _uiState.value = CartUiState.Loading
            try {
                val items = repository.getItems()
                val total = items.sumOf { it.price * it.qty }
                _uiState.value = CartUiState.Success(items, total)
            } catch (e: Exception) {
                _uiState.value = CartUiState.Error(e.message ?: "Error")
            }
        }
    }
}
