package com.example.pro1221_android_petshopmanagement.presentation.screen.view_model.kind

import com.example.pro1221_android_petshopmanagement.domain.model.Kind

sealed class KindEvent {
    data class DeleteKind(val kind:Kind):KindEvent()
    data class RestoreKind(val kind: Kind):KindEvent()
}