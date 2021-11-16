package com.example.pro1221_android_petshopmanagement.domain.use_case.kind

import com.example.pro1221_android_petshopmanagement.domain.model.Kind
import com.example.pro1221_android_petshopmanagement.domain.repository.KindRepository
import kotlinx.coroutines.flow.Flow

class AddKind(private val kindRepository: KindRepository) {
    suspend operator fun invoke(kind: Kind) = kindRepository.addKind(kind = kind)
}