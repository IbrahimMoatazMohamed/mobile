package com.example.DocEase.model.repositories

import kotlinx.coroutines.flow.Flow

interface BaseRepository<T> {
    suspend fun insert(t: T)

    suspend fun update(t: T)

    suspend fun delete(t: T)

    fun getOneStream(id: Int): Flow<T?>

    fun getAll(): Flow<List<T>>
}
