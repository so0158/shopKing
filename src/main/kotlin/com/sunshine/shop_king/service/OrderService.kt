package com.sunshine.shop_king.service

import com.sunshine.shop_king.dto.CreateOrderRequest
import com.sunshine.shop_king.dto.OrderDto
import com.sunshine.shop_king.dto.OrderItemDto
import com.sunshine.shop_king.entity.Order
import com.sunshine.shop_king.entity.OrderItem
import com.sunshine.shop_king.entity.OrderStatus
import com.sunshine.shop_king.entity.User
import com.sunshine.shop_king.repository.OrderRepository
import com.sunshine.shop_king.repository.ProductRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val productRepository: ProductRepository,
    private val userService: UserService
) {

    @Transactional
    fun createOrder(userId: Long, request: CreateOrderRequest): OrderDto {
        val user = userService.findById(userId)
        val orderItems = mutableListOf<OrderItem>()
        var totalAmount = BigDecimal.ZERO

        val order = Order(
            user = user,
            totalAmount = BigDecimal.ZERO,
            shippingAddress = request.shippingAddress
        )
        val savedOrder = orderRepository.save(order)

        request.orderItems.forEach { itemRequest ->
            val product = productRepository.findById(itemRequest.productId).orElseThrow {
                NoSuchElementException("Product not found with id: ${itemRequest.productId}")
            }

            if (product.stockQuantity < itemRequest.quantity) {
                throw IllegalArgumentException("Insufficient stock for product: ${product.name}")
            }

            val itemTotalPrice = product.price.multiply(BigDecimal.valueOf(itemRequest.quantity.toLong()))
            totalAmount = totalAmount.add(itemTotalPrice)

            val orderItem = OrderItem(
                order = savedOrder,
                product = product,
                quantity = itemRequest.quantity,
                unitPrice = product.price,
                totalPrice = itemTotalPrice
            )
            orderItems.add(orderItem)
        }

        val finalOrder = Order(
            id = savedOrder.id,
            user = user,
            totalAmount = totalAmount,
            shippingAddress = request.shippingAddress,
            orderItems = orderItems,
            createdAt = savedOrder.createdAt
        )

        val result = orderRepository.save(finalOrder)
        return result.toDto()
    }

    fun getOrdersByUser(userId: Long, pageable: Pageable): Page<OrderDto> {
        val user = userService.findById(userId)
        return orderRepository.findByUser(user, pageable).map { it.toDto() }
    }

    fun getOrderById(orderId: Long): OrderDto {
        val order = orderRepository.findById(orderId).orElseThrow {
            NoSuchElementException("Order not found with id: $orderId")
        }
        return order.toDto()
    }

    @Transactional
    fun updateOrderStatus(orderId: Long, status: OrderStatus): OrderDto {
        val order = orderRepository.findById(orderId).orElseThrow {
            NoSuchElementException("Order not found with id: $orderId")
        }

        val updatedOrder = Order(
            id = order.id,
            user = order.user,
            totalAmount = order.totalAmount,
            status = status,
            shippingAddress = order.shippingAddress,
            orderItems = order.orderItems,
            createdAt = order.createdAt
        )

        val savedOrder = orderRepository.save(updatedOrder)
        return savedOrder.toDto()
    }

    private fun Order.toDto(): OrderDto {
        return OrderDto(
            id = this.id,
            totalAmount = this.totalAmount,
            status = this.status,
            shippingAddress = this.shippingAddress,
            orderItems = this.orderItems.map { it.toDto() },
            createdAt = this.createdAt
        )
    }

    private fun OrderItem.toDto(): OrderItemDto {
        return OrderItemDto(
            id = this.id,
            productName = this.product.name,
            quantity = this.quantity,
            unitPrice = this.unitPrice,
            totalPrice = this.totalPrice
        )
    }
}