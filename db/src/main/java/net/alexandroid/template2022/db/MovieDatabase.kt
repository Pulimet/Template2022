package net.alexandroid.template2022.db

import androidx.room.Database
import androidx.room.RoomDatabase
import net.alexandroid.template2022.db.dao.MovieDao
import net.alexandroid.template2022.db.model.Movie

@Database(entities = [Movie::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}