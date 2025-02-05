package com.example.shoebox.data.shoeLocalDB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoeDao {
    @Query("SELECT * FROM shoes")
    fun getAllShoes(): Flow<List<ShoeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(shoes: List<ShoeEntity>)
}
