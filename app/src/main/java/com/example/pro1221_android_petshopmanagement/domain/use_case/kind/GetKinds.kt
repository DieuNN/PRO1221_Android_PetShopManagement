package com.example.pro1221_android_petshopmanagement.domain.use_case.kind

import com.example.pro1221_android_petshopmanagement.domain.model.Kind
import com.example.pro1221_android_petshopmanagement.domain.repository.KindRepository
import kotlinx.coroutines.flow.Flow

class GetKinds(private val kindRepository: KindRepository) {
     operator fun invoke(): Flow<List<Kind>> = kindRepository.getKinds()
}