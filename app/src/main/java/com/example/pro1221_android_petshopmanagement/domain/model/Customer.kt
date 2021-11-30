package com.example.pro1221_android_petshopmanagement.domain.model

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
@Entity(tableName = "table_customer")
data class Customer(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = 0,
    var name: String = "",
    var address:String = "",
    var phoneNumber:String = "",
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    var image: Bitmap? = null
)
