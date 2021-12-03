package com.example.pro1221_android_petshopmanagement.domain.model

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.ColumnInfo.BLOB
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.database.IgnoreExtraProperties

// FIXME: 11/30/21 We need to know who posted this, don't we?
// TODO: 12/4/21 add who posted next time if I can
@IgnoreExtraProperties
@Entity(tableName = "table_animal")
data class AnimalInfo(
    @PrimaryKey
    var name: String = "",
    var kind: String = "",
    @ColumnInfo(typeAffinity = BLOB)
    var image: Bitmap? = null,
    var detail: String = "",
    var updateTime: String = ""
)
