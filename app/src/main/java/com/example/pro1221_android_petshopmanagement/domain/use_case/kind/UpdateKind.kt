package com.example.pro1221_android_petshopmanagement.domain.use_case.kind

import com.example.pro1221_android_petshopmanagement.domain.model.Kind
import com.example.pro1221_android_petshopmanagement.domain.repository.KindRepository

class UpdateKind(private val kindRepository: KindRepository) {
    suspend operator fun invoke(kind: Kind) = kindRepository.updateKind(kind = kind)
}