package com.example.fifteenthhw.ui

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {
    @Query("SELECT * FROM word LIMIT 5")
    fun getAllWithLimit(): Flow<List<Word>>

    @Insert
    suspend fun addWord(word: Word)

    @Query("SELECT * FROM word WHERE meaning LIKE :thisWord")
    suspend fun getWord(thisWord: String): Word?

    @Query("DELETE FROM word")
    suspend fun delete()

    @Query("UPDATE word SET count = count+1 WHERE meaning  LIKE :newWord")
    suspend fun update(newWord: String)
}