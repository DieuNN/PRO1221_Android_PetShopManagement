package com.example.pro1221_android_petshopmanagement.data.data_source.dao

import androidx.room.*
import com.example.pro1221_android_petshopmanagement.domain.model.AnimalInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimalDao {
    @Query("SELECT * FROM table_animal")
    fun getAnimalsAsFlow(): Flow<List<AnimalInfo>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addAnimal(animal: AnimalInfo)

    @Update
    suspend fun updateAnimal(animal: AnimalInfo)

    @Query("delete from table_animal")
    suspend fun deleteAllRecord()

    @Delete
    suspend fun deleteAnimal(animal: AnimalInfo)

    @Query("Select * from table_animal where id = :id")
    suspend fun getAnimalById(id: Int): AnimalInfo

    @Query("select * from table_animal")
    fun getAnimalsAsList():List<AnimalInfo>
}