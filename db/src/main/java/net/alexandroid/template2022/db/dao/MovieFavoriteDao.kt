package net.alexandroid.template2022.db.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import net.alexandroid.template2022.db.model.movies.MovieFavorite

@Dao
interface MovieFavoriteDao {
    @Query("SELECT * from movies_favorite")
    fun getMovies(): Flow<List<MovieFavorite>>

    @Query("SELECT * from movies_favorite WHERE id = :movieId")
    fun getMovie(movieId: Int): Flow<MovieFavorite>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: MovieFavorite)

    @Delete
    suspend fun delete(movie: MovieFavorite)
}