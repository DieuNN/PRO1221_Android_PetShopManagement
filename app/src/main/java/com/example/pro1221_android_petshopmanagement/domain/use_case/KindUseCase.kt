package com.example.pro1221_android_petshopmanagement.domain.use_case

import com.example.pro1221_android_petshopmanagement.domain.use_case.kind.*

data class KindUseCase(
    var getKind: GetKind,
    var getKinds: GetKinds,
    var updateKind: UpdateKind,
    var deleteKind: DeleteKind,
    var addKind: AddKind
)
