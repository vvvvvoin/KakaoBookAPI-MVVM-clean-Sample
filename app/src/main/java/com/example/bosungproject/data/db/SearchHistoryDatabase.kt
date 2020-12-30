package com.example.bosungproject.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bosungproject.data.dao.SearchHistoryDao
import com.example.bosungproject.data.entity.SearchHistory

@Database(entities = [(SearchHistory::class)], version = 1)
abstract class SearchHistoryDatabase : RoomDatabase(){
    abstract fun searchHistoryDao() : SearchHistoryDao
}
