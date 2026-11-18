package com.example.tarea2tema2.model

import java.util.UUID

data class Product(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val isPurchased: Boolean = false,
)
