package com.sunshine.shop_king.dto

import java.math.BigDecimal

data class ProductDto(
    val id: Long,
    val name: String,
    val description: String?,
    val price: BigDecimal,
    val stockQuantity: Int,
    val imageUrl: String?,
    val categoryName: String,
    val isActive: Boolean
)

data class CreateProductRequest(
    val name: String,
    val description: String?,
    val price: BigDecimal,
    val stockQuantity: Int,
    val imageUrl: String?,
    val categoryId: Long
)

data class UpdateProductRequest(
    val name: String?,
    val description: String?,
    val price: BigDecimal?,
    val stockQuantity: Int?,
    val imageUrl: String?,
    val categoryId: Long?,
    val isActive: Boolean?
)