package com.example.pro1221_android_petshopmanagement.domain.model

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_animal")
data class AnimalInfo(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String = "",
    var kind: String = "",
    var image: ByteArray? = null,
    var detail: String = "",
    var updateTime: String = ""
)
