package com.sunshine.shop_king.entity

import jakarta.persistence.*

@Entity
@Table(name = "categories")
class Category(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val name: String,

    @Column
    val description: String? = null,

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    val products: List<Product> = emptyList()
)