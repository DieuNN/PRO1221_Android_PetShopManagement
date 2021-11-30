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

class CommonData {
    companion object {
        const val ANIMAL_INFO_NAME_KEY = "name"
        const val ANIMAL_INFO_KIND_KEY = "kind"
        const val ANIMAL_INFO_IMAGE_KEY = "image"
        const val ANIMAL_INFO_NOTE_KEY = "note"
        const val ANIMAL_INFO_ID_KEY = "id"
    }


    @DelicateCoroutinesApi
    suspend fun putAnimalDataIntoFirebase(context: Context) {
        val cloudDB = Firebase.firestore
        val animalsInfo = getAnimalInfoAsList(context = context)
        // delete all records of cloud db, prevent conflicts
        cloudDB.collection("common").get().addOnSuccessListener { documents ->
            for (document in documents) {
                document.reference.delete()
            }

            // for each animal info, put it on cloud db
            animalsInfo.forEach {
                cloudDB.collection("common").add(
                    hashMapOf(
                        ANIMAL_INFO_ID_KEY to it.id,
                        ANIMAL_INFO_NAME_KEY to it.name,
                        ANIMAL_INFO_KIND_KEY to it.kind,
                        ANIMAL_INFO_NOTE_KEY to it.detail,
                        ANIMAL_INFO_IMAGE_KEY to ImageUtil().convertBitmapToBase64(it.image!!)
                    )
                )
            }
            // save downloaded data into local db
            GlobalScope.launch {
                mapRetrievedAnimalInfoToLocal(context = context)
            }
        }

    }

    @DelicateCoroutinesApi
    suspend fun mapRetrievedAnimalInfoToLocal(context: Context) {
        val cloudDB = Firebase.firestore
        val localDB = AppDatabase.getInstance(context = context)

        // delete all records in local db, prevent conflict
        localDB.animalDao.deleteAllRecord()

        // retrieve data, then add into local db
        cloudDB.collection("common").get().addOnSuccessListener { documents ->
            for (document in documents) {
                Log.d("Data retrieved", "${document.id}, ${document.get("name")}")
                val animalInfo = AnimalInfo(
                    id = (document.get("id") as Long).toInt(),
                    name = document.get("name") as String,
                    image = ImageUtil().convertBase64ToBitmap(document.get("image") as String),
                    kind = document.get("kind") as String,
                    detail = document.get("note") as String,
                    updateTime = System.currentTimeMillis().toString()
                )
                GlobalScope.launch {
                    localDB.animalDao.addAnimal(animalInfo)
                }
            }
        }
    }
}