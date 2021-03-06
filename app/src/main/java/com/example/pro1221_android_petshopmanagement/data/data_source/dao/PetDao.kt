package com.example.pro1221_android_petshopmanagement.data.data_source.dao

import android.graphics.Bitmap
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.pro1221_android_petshopmanagement.domain.model.Pet
import kotlinx.coroutines.flow.Flow

@Dao
interface PetDao {
    @Query("SELECT * FROM table_pet")
    fun getPetsAsFlow(): Flow<List<Pet>>

    @Query("select * from table_pet")
    fun getPetsAsList():List<Pet>

    @Query("delete from table_pet")
    suspend fun deleteAllRecords()

    @Insert(onConflict = REPLACE)
    suspend fun addPet(pet: Pet)

    @Query("update table_pet set name = :petName, image = :petImage,price =:petPrice , kind = :petKind, detail = :petDetail where id = :id")
    suspend fun updatePet(id: Int, petName:String, petImage:Bitmap,petPrice:Int , petKind:String, petDetail:String)

    @Delete
    suspend fun deletePet(pet: Pet)

    @Query("select * from table_pet where id = :id")
    suspend fun getPetById(id: Int): Pet

    @Query("update table_pet set isSold = 1 where id =:id")
    suspend fun soldPet(id: Int)

    @Query("update table_pet set updateTime =:updateTime where id=:id")
    suspend fun updatePetUpdateTime(id: Int, updateTime: String)

    @Insert(onConflict = REPLACE)
    suspend fun restorePet(pet: Pet)

    @Query("update table_pet set customerName =:customerName where id =:id")
    suspend fun setCustomerName(customerName:String, id:Int)
}