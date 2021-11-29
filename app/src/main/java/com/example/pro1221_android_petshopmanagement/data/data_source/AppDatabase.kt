package com.example.pro1221_android_petshopmanagement.data.data_source

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pro1221_android_petshopmanagement.common.collections.Converters
import com.example.pro1221_android_petshopmanagement.data.data_source.dao.AnimalDao
import com.example.pro1221_android_petshopmanagement.data.data_source.dao.CustomerDao
import com.example.pro1221_android_petshopmanagement.data.data_source.dao.KindDao
import com.example.pro1221_android_petshopmanagement.data.data_source.dao.PetDao
import com.example.pro1221_android_petshopmanagement.domain.model.AnimalInfo
import com.example.pro1221_android_petshopmanagement.domain.model.Customer
import com.example.pro1221_android_petshopmanagement.domain.model.Kind
import com.example.pro1221_android_petshopmanagement.domain.model.Pet




@Database(
    entities = [AnimalInfo::class, Pet::class, Customer::class, Kind::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(value = [Converters::class])
abstract class AppDatabase : RoomDatabase() {
    abstract val animalDao: AnimalDao
    abstract val petDao: PetDao
    abstract val customerDao : CustomerDao
    abstract val kindDao:KindDao

    companion object {
        const val DB_NAME = "pet_shop_db"
        var INSTANCE:AppDatabase? = null
        fun getInstance(context: Context):AppDatabase {
             if (INSTANCE == null) {
                 synchronized(this) {
                     INSTANCE = Room.databaseBuilder(
                         context,
                         AppDatabase::class.java,
                         DB_NAME
                     ).allowMainThreadQueries().build()
                 }
             }
            return INSTANCE!!
        }
    }
}