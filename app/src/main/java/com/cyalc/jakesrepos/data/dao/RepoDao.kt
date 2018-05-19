package com.cyalc.jakesrepos.data.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.cyalc.jakesrepos.data.api.Repo
import io.reactivex.Flowable
import io.reactivex.Observable

@Dao
interface RepoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRepos(repositories: List<Repo>)

    @Query("SELECT * FROM repo")
    fun load(): Flowable<List<Repo>>
}
