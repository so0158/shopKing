package com.sunshine.shop_king.controller

import com.sunshine.shop_king.dto.CreateOrderRequest
import com.sunshine.shop_king.dto.OrderDto
import com.sunshine.shop_king.entity.OrderStatus
import com.sunshine.shop_king.service.OrderService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/orders")
class OrderController(
    private val orderService: OrderService
) {

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    fun createOrder(
        @RequestBody request: CreateOrderRequest,
        authentication: Authentication
    ): ResponseEntity<OrderDto> {
        val userId = getUserIdFromAuthentication(authentication)
        val order = orderService.createOrder(userId, request)
        return ResponseEntity(order, HttpStatus.CREATED)
    }

    @GetMapping("/my-orders")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    fun getMyOrders(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        authentication: Authentication
    ): ResponseEntity<Page<OrderDto>> {
        val userId = getUserIdFromAuthentication(authentication)
        val pageable = PageRequest.of(page, size)
        val orders = orderService.getOrdersByUser(userId, pageable)
        return ResponseEntity.ok(orders)
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    fun getOrderById(@PathVariable id: Long): ResponseEntity<OrderDto> {
        val order = orderService.getOrderById(id)
        return ResponseEntity.ok(order)
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    fun updateOrderStatus(
        @PathVariable id: Long,
        @RequestParam status: OrderStatus
    ): ResponseEntity<OrderDto> {
        val order = orderService.updateOrderStatus(id, status)
        return ResponseEntity.ok(order)
    }

    private fun getUserIdFromAuthentication(authentication: Authentication): Long {
        return 1L
    }
}