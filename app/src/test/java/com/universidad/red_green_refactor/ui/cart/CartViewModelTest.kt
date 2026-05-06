package com.universidad.red_green_refactor.ui.cart

import app.cash.turbine.test
import com.universidad.red_green_refactor.domain.model.CartItem
import com.universidad.red_green_refactor.domain.repository.CartRepository
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockKExtension::class)
class CartViewModelTest {

    @MockK
    lateinit var repository: CartRepository

    @MockK
    lateinit var analyticsService: AnalyticsService

    private lateinit var viewModel: CartViewModel

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        viewModel = CartViewModel(repository, analyticsService)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadCart emits Success state with items and total`() = runTest {
        val items = listOf(
            CartItem("1", "Libro", 25.0, 2),
            CartItem("2", "Pen", 5.0, 1)
        )
        coEvery { repository.getItems() } returns items

        viewModel.loadCart()

        val state = viewModel.uiState.value as CartUiState.Success
        assertEquals(2, state.items.size)
        assertEquals(55.0, state.total, 0.001) // 25*2 + 5*1
    }

    @Test
    fun `loadCart emits Error when repository throws`() = runTest {
        coEvery { repository.getItems() } throws IOException("sin red")
        viewModel.loadCart()
        assertTrue(viewModel.uiState.value is CartUiState.Error)
    }

    @Test
    fun `loadCart emits Loading before Success`() = runTest {
        coEvery { repository.getItems() } returns emptyList()
        viewModel.uiState.test {
            viewModel.loadCart()

            assertEquals(CartUiState.Loading, awaitItem())

            assertTrue(awaitItem() is CartUiState.Success)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `calculateTotal returns 0 for empty list`() {
        val total = viewModel.calculateTotal(emptyList())
        assertEquals(0.0, total, 0.001)
    }
}
