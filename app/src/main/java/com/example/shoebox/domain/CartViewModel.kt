package com.example.shoebox.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoebox.data.shoeLocalDB.CartDao
import com.example.shoebox.data.shoeLocalDB.CartEntity
import com.example.shoebox.data.shoeModel.CartItem
import com.example.shoebox.data.shoeModel.Shoe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CartViewModel(private val cartDao: CartDao) : ViewModel() {
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    val totalPrice: StateFlow<Double> = _cartItems.map { items ->
        items.sumOf { it.shoe.price * it.quantity }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0.0)

    init {
        viewModelScope.launch {
            cartDao.getCartWithDetails().collect { entities ->
                _cartItems.value = entities.map { it.toCartItem() }
            }
        }
    }

    fun addToCart(shoe: Shoe) {
        viewModelScope.launch {
            val existing = _cartItems.value.find { it.shoe.id == shoe.id }
            val newItem = existing?.copy(quantity = existing.quantity + 1) ?: CartItem(shoe, 1)
            cartDao.insertOrUpdate(CartEntity(newItem.shoe.id, newItem.quantity))
        }
    }

    fun removeFromCart(item: CartItem) {
        viewModelScope.launch {
            cartDao.removeFromCart(item.shoe.id)
        }
    }

}