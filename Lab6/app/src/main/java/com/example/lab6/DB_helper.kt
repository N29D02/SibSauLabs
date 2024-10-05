package com.example.lab6

import android.content.Context
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers


@Entity(tableName = "crime")
data class Crime (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var title: String = "",
    var date: String = "",
    var isSolved: Boolean = false
)

@Dao
interface CrimeDao {
    @get:Query("SELECT * FROM crime")
    val all: List<Any?>?

    @Query("SELECT * FROM crime WHERE id = :id")
    fun getById(id: Long): Crime?

    @Insert
    fun insert(crime: Crime?)

    @Update
    fun update(crime: Crime?)

    @Delete
    fun delete(crime: Crime?)
}

@Database(entities = [Crime::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun crimeDao(): CrimeDao?
}

object DatabaseProvider {
    private var INSTANCE: CrimeDatabase? = null

    fun getDatabase(context: Context): CrimeDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                CrimeDatabase::class.java,
                "crime_database"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}