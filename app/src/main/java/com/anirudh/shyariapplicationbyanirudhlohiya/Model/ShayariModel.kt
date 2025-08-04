package com.anirudh.shyariapplicationbyanirudhlohiya.Model

import java.util.Date

data class ShayariModel(
    val id: String? = null,
    val data: String? = null,
    var isFavorite: Boolean? = false,
    val timestamp: Date? = null
)
