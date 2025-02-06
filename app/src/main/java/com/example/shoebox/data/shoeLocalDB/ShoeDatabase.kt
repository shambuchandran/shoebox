package com.example.shoebox.data.shoeLocalDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ShoeEntity::class, CartEntity::class], version = 1)
abstract class ShoeDatabase : RoomDatabase() {
    abstract fun shoeDao(): ShoeDao
    abstract fun cartDao(): CartDao

    companion object {
        @Volatile
        private var INSTANCE: ShoeDatabase? = null

        fun getInstance(context: Context): ShoeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ShoeDatabase::class.java,
                    "shoe_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}