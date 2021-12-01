package com.example.pro1221_android_petshopmanagement.data.data_source.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.pro1221_android_petshopmanagement.domain.model.Kind
import kotlinx.coroutines.flow.Flow

@Dao
interface KindDao {
    @Query("select * from table_kind")
    fun getKindsAsFlow(): Flow<List<Kind>>

    @Insert(onConflict = REPLACE)
    suspend fun addKind(kind: Kind)

    @Query("select * from table_kind")
    suspend fun getKindsAsList(): List<Kind>

    @Query("delete from table_kind")
    suspend fun deleteAllRecords()

    @Delete
    suspend fun deleteKind(kind: Kind)

    @Update
    suspend fun updateKind(kind: Kind)

    @Query("select * from table_kind where id =:id")
    fun getKind(id: Int): Kind

}