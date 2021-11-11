package com.example.pro1221_android_petshopmanagement.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_pet")
data class Pet(
    @PrimaryKey(autoGenerate = true)
    var id:Int? = null,
    var name:String = "",
    var kind:String = "",
    var detail:String = "",
    var isSold:Boolean = false,
    var updateTime:String = "",
    var image:ByteArray? = null,
    var price:Int = 0
)
