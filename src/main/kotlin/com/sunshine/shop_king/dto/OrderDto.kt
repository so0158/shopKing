package com.sunshine.shop_king.dto

import com.sunshine.shop_king.entity.OrderStatus
import java.math.BigDecimal
import java.time.LocalDateTime

data class OrderDto(
    val id: Long,
    val totalAmount: BigDecimal,
    val status: OrderStatus,
    val shippingAddress: String?,
    val orderItems: List<OrderItemDto>,
    val createdAt: LocalDateTime
)

data class OrderItemDto(
    val id: Long,
    val productName: String,
    val quantity: Int,
    val unitPrice: BigDecimal,
    val totalPrice: BigDecimal
)

data class CreateOrderRequest(
    val orderItems: List<CreateOrderItemRequest>,
    val shippingAddress: String?
)

data class CreateOrderItemRequest(
    val productId: Long,
    val quantity: Int
)