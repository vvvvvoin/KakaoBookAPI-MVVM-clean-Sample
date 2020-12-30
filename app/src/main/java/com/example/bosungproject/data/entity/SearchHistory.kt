package com.example.bosungproject.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SearchHistory(
    @PrimaryKey
    @ColumnInfo(name = "query")
    val query: String
)