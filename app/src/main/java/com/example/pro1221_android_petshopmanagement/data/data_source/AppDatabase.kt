package com.example.pro1221_android_petshopmanagement.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pro1221_android_petshopmanagement.data.data_source.dao.AnimalDao
import com.example.pro1221_android_petshopmanagement.data.data_source.dao.PetDao
import com.example.pro1221_android_petshopmanagement.domain.model.AnimalInfo
import com.example.pro1221_android_petshopmanagement.domain.model.Pet

@Database(entities = [AnimalInfo::class, Pet::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val animalDao:AnimalDao
    abstract val petDao:PetDao

    companion object {
        const val DB_NAME = "pet_shop_db"
    }
}