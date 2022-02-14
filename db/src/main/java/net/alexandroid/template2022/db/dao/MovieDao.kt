package net.alexandroid.template2022.db.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import net.alexandroid.template2022.db.model.Movie

@Dao
interface MovieDao {
    @Query("SELECT * from movies")
    fun getMovies(): Flow<List<Movie>>

    @Query("SELECT * from movies WHERE id = :movieId")
    fun getMovie(movieId: Int): Flow<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: Collection<Movie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: Movie)

    @Query("DELETE FROM movies")
    suspend fun deleteAll()
}