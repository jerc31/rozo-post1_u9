package com.universidad.red_green_refactor.ui.cart

import androidx.lifecycle.ViewModel
import com.universidad.red_green_refactor.domain.repository.CartRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CartViewModel(
    private val repository: CartRepository,
    private val analytics: AnalyticsService
) : ViewModel() {
    private val _uiState = MutableStateFlow<CartUiState>(CartUiState.Loading)
    val uiState: StateFlow<CartUiState> = _uiState.asStateFlow()

    fun loadCart() {
        // Red state: No implementation
    }
}
