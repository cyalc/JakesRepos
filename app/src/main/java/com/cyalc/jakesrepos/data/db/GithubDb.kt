package com.cyalc.jakesrepos.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.cyalc.jakesrepos.commons.Converters
import com.cyalc.jakesrepos.data.models.Repo
import com.cyalc.jakesrepos.data.dao.RepoDao

@Database(
        entities = [Repo::class],
        version = 1,
        exportSchema = false
)
@TypeConverters(Converters::class)
abstract class GithubDb : RoomDatabase() {
    abstract fun repoDao(): RepoDao
}
