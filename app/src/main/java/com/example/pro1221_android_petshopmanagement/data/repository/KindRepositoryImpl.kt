package com.example.pro1221_android_petshopmanagement.data.repository

import com.example.pro1221_android_petshopmanagement.data.data_source.dao.KindDao
import com.example.pro1221_android_petshopmanagement.domain.model.Kind
import com.example.pro1221_android_petshopmanagement.domain.repository.KindRepository
import kotlinx.coroutines.flow.Flow

class KindRepositoryImpl(private val kindDao: KindDao) : KindRepository {
    override fun getKinds(): Flow<List<Kind>> {
        return kindDao.getKinds()
    }

    override suspend fun addKind(kind: Kind) {
        kindDao.addKind(kind = kind)
    }

    override suspend fun deleteKind(kind: Kind) {
        kindDao.deleteKind(kind = kind)
    }

    override suspend fun updateKind(kind: Kind) {
        kindDao.updateKind(kind = kind)
    }

    override fun getKind(id: Int): Kind {
        return kindDao.getKind(id = id)
    }
}