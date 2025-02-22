package com.cowday.pawtography.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cowday.pawtography.models.local.DogEntity

@Database(
    entities = [
        DogEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class DogDatabase: RoomDatabase() {
    abstract fun dogDao(): DogDao

    companion object {
        private const val DOG_DATABASE = "dog_database"

        @Volatile
        private var INSTANCE: DogDatabase? = null

        fun getDatabase(context: Context): DogDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    DogDatabase::class.java,
                    DOG_DATABASE
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}