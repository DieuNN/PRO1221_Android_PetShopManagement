package com.example.pro1221_android_petshopmanagement.data.data_source.firebase

import android.content.Context
import com.example.pro1221_android_petshopmanagement.data.data_source.AppDatabase
import com.example.pro1221_android_petshopmanagement.domain.model.AnimalInfo
import kotlinx.coroutines.DelicateCoroutinesApi


fun getAnimalInfoAsList(context: Context): List<AnimalInfo> {
    return AppDatabase.getInstance(context = context).animalDao.getAnimalsAsList()
}

