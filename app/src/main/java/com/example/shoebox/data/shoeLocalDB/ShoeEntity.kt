package com.example.shoebox.data.shoeLocalDB

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.shoebox.data.shoeModel.Shoe

@Entity(tableName = "shoes")
data class ShoeEntity(
    @PrimaryKey val id: String,
    val name: String,
    val price: Double,
    val description: String,
    val imageId: Int
)
fun ShoeEntity.toShoe() = Shoe(id, name, price, description, imageId)
fun Shoe.toEntity() = ShoeEntity(id, name, price, description, imageId)


@Entity(
    tableName = "cart_items",
    foreignKeys = [ForeignKey(
        entity = ShoeEntity::class,
        parentColumns = ["id"],
        childColumns = ["shoeId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class CartEntity(
    @PrimaryKey val shoeId: String,
    val quantity: Int
)