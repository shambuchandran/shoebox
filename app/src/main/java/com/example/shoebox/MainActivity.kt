package com.example.shoebox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shoebox.data.api.MockShoeApi
import com.example.shoebox.data.shoeLocalDB.ShoeDatabase
import com.example.shoebox.domain.CartViewModel
import com.example.shoebox.domain.ShoeRepository
import com.example.shoebox.domain.ShoeViewModel
import com.example.shoebox.presentation.ShoeAppNavigation

class MainActivity : ComponentActivity() {
    private lateinit var shoeViewModel: ShoeViewModel
    private lateinit var cartViewModel: CartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val db = ShoeDatabase.getInstance(this)
        val repository = ShoeRepository(db.shoeDao(), MockShoeApi())

        shoeViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ShoeViewModel(repository) as T
            }
        })[ShoeViewModel::class.java]

        cartViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return CartViewModel(db.cartDao()) as T
            }
        })[CartViewModel::class.java]

        setContent {
            ShoeAppNavigation(shoeViewModel, cartViewModel)
        }
    }
}