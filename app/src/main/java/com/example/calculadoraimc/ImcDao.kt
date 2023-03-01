package com.example.calculadoraimc

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ImcDao {
    @Insert
    suspend fun insert(imcEntity: ImcEntity)

    @Delete
    suspend fun delete(imcEntity: ImcEntity)

    @Query("SELECT * FROM `history-table`")
    fun getAll(): Flow<List<ImcEntity>>

    @Query("SELECT * FROM `history-table` where id=:id")
    fun getAllById(id:Int):Flow<ImcEntity>
}