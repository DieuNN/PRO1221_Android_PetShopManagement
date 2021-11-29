package com.example.pro1221_android_petshopmanagement.data.data_source.firebase

import android.content.Context
import android.util.Base64
import android.util.Log
import com.example.pro1221_android_petshopmanagement.common.collections.Converters
import com.example.pro1221_android_petshopmanagement.data.data_source.AppDatabase
import com.example.pro1221_android_petshopmanagement.domain.model.AnimalInfo
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


fun getAnimalInfoAsList(context: Context): List<AnimalInfo> {
    return AppDatabase.getInstance(context = context).animalDao.getAnimalsAsList()
}


fun putDataTest(string: String, context: Context) {
    val database = Firebase.database
    val myRef = database.getReference("common")

    getAnimalInfoAsList(context = context).forEach {
        myRef.setValue(
            AnimalInfo(
                name = it.name,
                detail = it.detail,
                updateTime = it.updateTime,
                kind = it.kind,
                id = it.id,
                byteArrayAsString = Base64.encodeToString(
                    Converters().fromBitmapToByteArray(it.image!!),
                    Base64.DEFAULT
                )
            )
        )
    }


}

fun receiveData() {
    val database = Firebase.database
    val myRef = database.getReference("message")
    myRef.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            // This method is called once with the initial value and again
            // whenever data at this location is updated.
            val value = dataSnapshot.value
            Log.d("receive test", "Value is: $value")
        }

        override fun onCancelled(error: DatabaseError) {
            // Failed to read value
            Log.w("receive test", "Failed to read value.", error.toException())
        }
    })
}