package com.example.lab6

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Update

@Entity(tableName = "suspects")
data class Suspect(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val firstName: String,
    val lastName: String
)

@Dao
interface SuspectDao {
    @Insert
    suspend fun insertSuspect(suspect: Suspect)

    @Query("SELECT * FROM suspects")
    suspend fun getAllSuspects(): List<Suspect>
}

@Database(entities = [Suspect::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun suspectDao(): SuspectDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}