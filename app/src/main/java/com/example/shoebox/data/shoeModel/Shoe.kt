package com.example.shoebox.data.shoeModel

data class Shoe(
    val id: String,
    val name: String,
    val price: Double,
    val description: String,
    val imageId: Int
)

data class CartItem(
    val shoe: Shoe,
    var quantity: Int
)