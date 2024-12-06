package com.example.lab7.database

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

@Entity(tableName = "crimes")
data class Crime(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var title: String = "",
    var uri: String? = null,
    var date: String = "",
    var isSolved: Boolean = false,
    var suspect_name: String = ""
)

@Dao
interface SuspectDao {
    @Insert
    suspend fun insertSuspect(suspect: Suspect)

    @Query("SELECT * FROM suspects")
    suspend fun getAllSuspects(): List<Suspect>

    @Query("SELECT * FROM suspects")
    fun getAllSuspectsLiveData(): LiveData<List<Suspect>>

    @Query("DELETE FROM suspects")
    suspend fun deleteAllSuspects()
}

@Dao
interface CrimeDao {
    @Insert
    suspend fun insertCrime(crime: Crime)

    @Query("SELECT * FROM crimes")
    suspend fun getAllCrimes(): List<Crime>

    @Query("SELECT * FROM crimes")
    fun getAllCrimesLiveData(): LiveData<List<Crime>>

    @Query("DELETE FROM crimes")
    suspend fun deleteAllCrimes()

    @Query("DELETE FROM crimes WHERE ID = :crimeId")
    suspend fun deleteCrimeById(crimeId: Long)
}

@Database(entities = [Suspect::class, Crime::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun suspectDao(): SuspectDao
    abstract fun crimeDao(): CrimeDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}