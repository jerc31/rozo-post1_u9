package com.universidad.red_green_refactor.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.universidad.red_green_refactor.domain.model.CartItem
import com.universidad.red_green_refactor.domain.repository.CartRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

class CartViewModel(
    private val repository: CartRepository,
    private val analytics: AnalyticsService
) : ViewModel() {
    private val _uiState = MutableStateFlow<CartUiState>(CartUiState.Loading)
    val uiState: StateFlow<CartUiState> = _uiState.asStateFlow()

    fun loadCart() {
        viewModelScope.launch {
            _uiState.value = CartUiState.Loading
            _uiState.value = runCatching { repository.getItems() }
                .map { items -> CartUiState.Success(items, calculateTotal(items)) }
                .getOrElse { e -> CartUiState.Error(errorMessage(e)) }
        }
    }

    // Función pura — fácilmente testeable por separado
    internal fun calculateTotal(items: List<CartItem>) =
        items.sumOf { it.price * it.qty }

    private fun errorMessage(e: Throwable) = when (e) {
        is IOException -> "Sin conexión. Verificar red."
        else -> e.message ?: "Error inesperado"
    }
}
