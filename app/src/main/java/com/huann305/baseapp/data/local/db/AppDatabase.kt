package com.huann305.baseapp.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.huann305.baseapp.data.local.dao.IDao
import com.huann305.baseapp.data.model.Item

@Database(entities = [Item::class], version = AppDatabase.version, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun iDao(): IDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        const val version = 1
        const val DATABASE_DEFAULT = "db$version.db"

        @JvmStatic
        fun getInstance(context: Context, dbName: String = DATABASE_DEFAULT): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, dbName
                )
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
