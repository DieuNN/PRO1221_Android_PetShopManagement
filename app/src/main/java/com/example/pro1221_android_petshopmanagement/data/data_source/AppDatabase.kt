package com.example.pro1221_android_petshopmanagement.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pro1221_android_petshopmanagement.common.collections.Converters
import com.example.pro1221_android_petshopmanagement.data.data_source.dao.AnimalDao
import com.example.pro1221_android_petshopmanagement.data.data_source.dao.CustomerDao
import com.example.pro1221_android_petshopmanagement.data.data_source.dao.PetDao
import com.example.pro1221_android_petshopmanagement.domain.model.AnimalInfo
import com.example.pro1221_android_petshopmanagement.domain.model.Customer
import com.example.pro1221_android_petshopmanagement.domain.model.Pet

@Database(
    entities = [AnimalInfo::class, Pet::class, Customer::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(value = [Converters::class])
abstract class AppDatabase : RoomDatabase() {
    abstract val animalDao: AnimalDao
    abstract val petDao: PetDao
    abstract val customerDao : CustomerDao

    companion object {
        const val DB_NAME = "pet_shop_db"
    }
}