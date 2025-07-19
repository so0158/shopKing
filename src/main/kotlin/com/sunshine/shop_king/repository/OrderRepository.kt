package com.sunshine.shop_king.repository

import com.sunshine.shop_king.entity.Order
import com.sunshine.shop_king.entity.OrderStatus
import com.sunshine.shop_king.entity.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : JpaRepository<Order, Long> {
    fun findByUser(user: User, pageable: Pageable): Page<Order>
    fun findByStatus(status: OrderStatus, pageable: Pageable): Page<Order>
    fun findByUserAndStatus(user: User, status: OrderStatus, pageable: Pageable): Page<Order>
}