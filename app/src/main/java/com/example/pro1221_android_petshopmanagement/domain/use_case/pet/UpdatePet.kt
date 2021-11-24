package com.example.pro1221_android_petshopmanagement.domain.use_case.pet

import android.graphics.Bitmap
import com.example.pro1221_android_petshopmanagement.domain.repository.PetRepository

class UpdatePet(private val petRepository: PetRepository) {
    suspend operator fun invoke(
        id: Int,
        name: String,
        image: Bitmap,
        price:Int,
        kind: String,
        detail: String
    ) = petRepository.updatePet(
        id = id,
        petName = name,
        petImage = image,
        petPrice = price,
        petKind = kind,
        petDetail = detail
    )
}