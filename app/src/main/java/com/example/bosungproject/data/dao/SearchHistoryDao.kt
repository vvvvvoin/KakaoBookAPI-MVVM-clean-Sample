package com.example.bosungproject.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bosungproject.data.entity.SearchHistory
import io.reactivex.Single

@Dao
interface SearchHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(searchWord: SearchHistory) : Single<Long>

    @Query("Select * From searchHistory")
    fun getAllSearchHistory() : Single<List<SearchHistory>>
}