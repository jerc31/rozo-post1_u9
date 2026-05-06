package com.universidad.red_green_refactor.ui.cart

import com.universidad.red_green_refactor.domain.model.CartItem

sealed class CartUiState {
    object Loading : CartUiState()
    data class Success(val items: List<CartItem>, val total: Double) : CartUiState()
    data class Error(val message: String) : CartUiState()
}
