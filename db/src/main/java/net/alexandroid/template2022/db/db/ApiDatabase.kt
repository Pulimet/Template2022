package net.alexandroid.template2022.db.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import net.alexandroid.template2022.db.dao.ApiDao
import net.alexandroid.template2022.db.model.api.Api
import net.alexandroid.template2022.db.model.api.ParamsConverter

@Database(entities = [Api::class], version = 1, exportSchema = true)
@TypeConverters(ParamsConverter::class)
abstract class ApiDatabase : RoomDatabase() {
    abstract fun apiDao(): ApiDao
}