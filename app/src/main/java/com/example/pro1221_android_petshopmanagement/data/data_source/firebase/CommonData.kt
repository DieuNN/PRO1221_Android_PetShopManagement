package com.example.pro1221_android_petshopmanagement.data.data_source.firebase

import android.content.Context
import android.util.Log
import com.example.pro1221_android_petshopmanagement.common.collections.ImageUtil
import com.example.pro1221_android_petshopmanagement.data.data_source.AppDatabase
import com.example.pro1221_android_petshopmanagement.domain.model.AnimalInfo
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CommonData(
    private val context: Context
) {
    companion object {
        const val ANIMAL_INFO_NAME_KEY = "name"
        const val ANIMAL_INFO_KIND_KEY = "kind"
        const val ANIMAL_INFO_IMAGE_KEY = "image"
        const val ANIMAL_INFO_NOTE_KEY = "note"
        const val ANIMAL_INFO_ID_KEY = "id"
    }

    private val cloudDB = Firebase.firestore
    private val localDB = AppDatabase.getInstance(context = context)

    @DelicateCoroutinesApi
    suspend fun syncWhenLogin() {
        // clear local first
        localDB.animalDao.deleteAllRecord()

        cloudDB.collection("common").get().addOnSuccessListener { documents ->
            for (document in documents) {
                Log.d("Data retrieved", "${document.id}, ${document.get("name")}")
                val animalInfo = AnimalInfo(
                    name = document.get(ANIMAL_INFO_NAME_KEY) as String,
                    image = ImageUtil().convertBase64ToBitmap(document.get(ANIMAL_INFO_IMAGE_KEY) as String),
                    kind = document.get(ANIMAL_INFO_KIND_KEY) as String,
                    detail = document.get(ANIMAL_INFO_NOTE_KEY) as String,
                    updateTime = System.currentTimeMillis().toString()
                )
                GlobalScope.launch {
                    localDB.animalDao.addAnimal(animalInfo)
                }
            }
        }
    }

    @DelicateCoroutinesApi
    suspend fun syncWhenPressSync() {
        // clear cloud -> add to it again
        val animalsInfo = localDB.animalDao.getAnimalsAsList()
        // delete all records of cloud db,
        cloudDB.collection("common").get().addOnSuccessListener { documents ->
            for (document in documents) {
                document.reference.delete()
            }

            // for each animal info, put it on cloud db
            animalsInfo.forEach {
                cloudDB.collection("common").add(
                    hashMapOf(
                        ANIMAL_INFO_NAME_KEY to it.name,
                        ANIMAL_INFO_KIND_KEY to it.kind,
                        ANIMAL_INFO_NOTE_KEY to it.detail,
                        ANIMAL_INFO_IMAGE_KEY to ImageUtil().convertBitmapToBase64(it.image!!)
                    )
                )
            }
        }
    }

    @DelicateCoroutinesApi
    suspend fun syncWhenLogout() {
        // clear cloud -> put data form local to cloud -> clear local data
        val animalsInfo = localDB.animalDao.getAnimalsAsList()
        cloudDB.collection("common").get().addOnSuccessListener { documents ->
            for (document in documents) {
                document.reference.delete()
            }

            // for each animal info, put it on cloud db
            animalsInfo.forEach {
                cloudDB.collection("common").add(
                    hashMapOf(
                        ANIMAL_INFO_NAME_KEY to it.name,
                        ANIMAL_INFO_KIND_KEY to it.kind,
                        ANIMAL_INFO_NOTE_KEY to it.detail,
                        ANIMAL_INFO_IMAGE_KEY to ImageUtil().convertBitmapToBase64(it.image!!)
                    )
                )
            }
            GlobalScope.launch {
                localDB.animalDao.deleteAllRecord()
            }
        }
    }
}