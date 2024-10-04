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
    @PrimaryKey
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

    // реализуем синглтон
    companion object {
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {

            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "User_database"

                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}

class CrimeRepository(private val crimeDao: CrimeDao) {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    val userList: LiveData<List<Crime>>? = crimeDao.all

    fun addCrime(Crime: Crime) {
        coroutineScope.launch(Dispatchers.IO) {
            userDao.addUser(User)
        }
    }

    fun deleteUser(id:Int) {
        coroutineScope.launch(Dispatchers.IO) {
            userDao.deleteUser(id)
        }
    }
}