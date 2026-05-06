package com.universidad.red_green_refactor.domain.repository

import com.universidad.red_green_refactor.domain.model.CartItem

interface CartRepository {
    suspend fun getItems(): List<CartItem>
    suspend fun addItem(item: CartItem): Boolean
    suspend fun removeItem(id: String): Boolean
}
