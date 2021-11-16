package com.example.pro1221_android_petshopmanagement.domain.repository

import com.example.pro1221_android_petshopmanagement.domain.model.Kind
import kotlinx.coroutines.flow.Flow

interface KindRepository {

    fun getKinds(): Flow<List<Kind>>

    suspend fun addKind(kind: Kind)

    suspend fun deleteKind(kind: Kind)

    suspend fun updateKind(kind: Kind)

    fun getKind(id: Int): Kind
}