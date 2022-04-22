package net.alexandroid.template2022.db.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import net.alexandroid.template2022.db.model.api.Api

@Dao
interface ApiDao {
    @Query("SELECT * from api")
    fun getApis(): Flow<List<Api>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(api: Api)

    @Delete
    suspend fun delete(api: Api)
}