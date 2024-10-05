package com.example.lab6

import android.content.Context
import android.net.Uri
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

@Entity(tableName = "suspect")
data class Suspect(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var name: String = "",
    var surname: String = ""
)

@Dao
interface SuspectDao {

    @get:Query("SELECT * FROM suspect")
    val all: LiveData<List<Suspect>>

    @Query("SELECT * FROM suspect WHERE id = :id")
    fun getById(id: Long): Suspect?

    @Insert
    fun insert(suspect: Suspect)

    @Update
    fun update(suspect: Suspect)

    @Delete
    fun delete(suspect: Suspect)

    fun testInitialize(){
        val suspect1 = Suspect(name = "John", surname = "Doe")
        val suspect2 = Suspect(name = "Mark", surname = "Shader")
        val suspect3 = Suspect(name = "Steven", surname = "Peterson")

        insert(suspect1)
        insert(suspect2)
        insert(suspect3)
    }
}


@Entity(tableName = "crime")
data class Crime(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var title: String = "",
    var uri: String? = null,
    var date: String = "",
    var isSolved: Boolean = false
)

@Dao
interface CrimeDao {
    @get:Query("SELECT * FROM crime")
    val all: List<Crime?>?

    @Query("SELECT * FROM crime WHERE id = :id")
    fun getById(id: Long): Crime?

    @Insert
    fun insert(crime: Crime)

    @Update
    fun update(crime: Crime)

    @Delete
    fun delete(crime: Crime)
}

@Database(entities = [Crime::class, Suspect::class], version = 1)
abstract class CrimeDatabase : RoomDatabase() {
    abstract fun crimeDao(): CrimeDao
    abstract fun suspectDao(): SuspectDao
}

