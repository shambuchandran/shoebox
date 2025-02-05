package com.example.shoebox.domain

import com.example.shoebox.data.api.MockShoeApi
import com.example.shoebox.data.shoeLocalDB.ShoeDao
import com.example.shoebox.data.shoeLocalDB.toEntity
import com.example.shoebox.data.shoeLocalDB.toShoe
import com.example.shoebox.data.shoeModel.Shoe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class ShoeRepository(
    private val shoeDao: ShoeDao,
    private val api: MockShoeApi
) {
    suspend fun fetchShoes() {
        val localShoes = shoeDao.getAllShoes().first()
        if (localShoes.isEmpty()) {
            val shoes = api.getShoes()
            shoeDao.insertAll(shoes.map { it.toEntity() })
        }
    }

    fun getShoes(): Flow<List<Shoe>> = shoeDao.getAllShoes().map { list ->
        list.map { it.toShoe() }
    }
}