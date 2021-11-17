package com.example.pro1221_android_petshopmanagement.domain.model

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.ColumnInfo.BLOB
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_animal")
data class AnimalInfo(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = 0,
    var name: String = "",
    var kind: String = "",
    @ColumnInfo(typeAffinity = BLOB)
    var image: Bitmap? = null,
    var detail: String = "",
    var updateTime: String = ""
)
