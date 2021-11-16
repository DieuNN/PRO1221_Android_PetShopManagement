package com.example.pro1221_android_petshopmanagement.domain.use_case.kind

import com.example.pro1221_android_petshopmanagement.domain.repository.KindRepository

class GetKind(private val kindRepository: KindRepository) {
    suspend operator fun invoke(id: Int) = kindRepository.getKind(id = id)
}