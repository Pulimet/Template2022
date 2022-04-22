package net.alexandroid.template2022.db.db

import androidx.room.Database
import androidx.room.RoomDatabase
import net.alexandroid.template2022.db.dao.MovieDao
import net.alexandroid.template2022.db.dao.MovieFavoriteDao
import net.alexandroid.template2022.db.model.movies.Movie
import net.alexandroid.template2022.db.model.movies.MovieFavorite

@Database(entities = [Movie::class, MovieFavorite::class], version = 1, exportSchema = true)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun movieFavoriteDao(): MovieFavoriteDao
}