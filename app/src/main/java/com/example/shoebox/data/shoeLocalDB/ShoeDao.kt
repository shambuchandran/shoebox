package com.example.shoebox.data.shoeLocalDB

import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shoebox.data.shoeModel.CartItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoeDao {
    @Query("SELECT * FROM shoes")
    fun getAllShoes(): Flow<List<ShoeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(shoes: List<ShoeEntity>)
}

@Dao
interface CartDao {
    @Query("SELECT * FROM cart_items")
    fun getCartItems(): Flow<List<CartEntity>>

    @Query("""
        SELECT shoes.*, cart_items.quantity 
        FROM shoes 
        INNER JOIN cart_items ON shoes.id = cart_items.shoeId
    """)
    fun getCartWithDetails(): Flow<List<CartItemWithDetails>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(cartItem: CartEntity)

    @Query("DELETE FROM cart_items WHERE shoeId = :shoeId")
    suspend fun removeFromCart(shoeId: String)

}

data class CartItemWithDetails(
    @Embedded val shoe: ShoeEntity,
    val quantity: Int
) {
    fun toCartItem() = CartItem(shoe.toShoe(), quantity)
}