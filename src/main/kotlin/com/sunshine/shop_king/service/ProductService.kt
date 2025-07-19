package com.sunshine.shop_king.service

import com.sunshine.shop_king.dto.CreateProductRequest
import com.sunshine.shop_king.dto.ProductDto
import com.sunshine.shop_king.dto.UpdateProductRequest
import com.sunshine.shop_king.entity.Product
import com.sunshine.shop_king.repository.CategoryRepository
import com.sunshine.shop_king.repository.ProductRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository
) {

    fun getAllProducts(pageable: Pageable): Page<ProductDto> {
        return productRepository.findByIsActiveTrue(pageable).map { it.toDto() }
    }

    fun getProductById(id: Long): ProductDto {
        val product = productRepository.findById(id).orElseThrow { 
            NoSuchElementException("Product not found with id: $id") 
        }
        return product.toDto()
    }

    fun searchProducts(name: String, pageable: Pageable): Page<ProductDto> {
        return productRepository.findByNameContainingAndIsActiveTrue(name, pageable).map { it.toDto() }
    }

    fun getProductsByCategoryId(categoryId: Long, pageable: Pageable): Page<ProductDto> {
        val category = categoryRepository.findById(categoryId).orElseThrow { 
            NoSuchElementException("Category not found with id: $categoryId") 
        }
        return productRepository.findByCategoryAndIsActiveTrue(category, pageable).map { it.toDto() }
    }

    @Transactional
    fun createProduct(request: CreateProductRequest): ProductDto {
        val category = categoryRepository.findById(request.categoryId).orElseThrow { 
            NoSuchElementException("Category not found with id: ${request.categoryId}") 
        }

        val product = Product(
            name = request.name,
            description = request.description,
            price = request.price,
            stockQuantity = request.stockQuantity,
            imageUrl = request.imageUrl,
            category = category
        )

        val savedProduct = productRepository.save(product)
        return savedProduct.toDto()
    }

    @Transactional
    fun updateProduct(id: Long, request: UpdateProductRequest): ProductDto {
        val product = productRepository.findById(id).orElseThrow { 
            NoSuchElementException("Product not found with id: $id") 
        }

        val category = request.categoryId?.let { categoryId ->
            categoryRepository.findById(categoryId).orElseThrow { 
                NoSuchElementException("Category not found with id: $categoryId") 
            }
        } ?: product.category

        val updatedProduct = Product(
            id = product.id,
            name = request.name ?: product.name,
            description = request.description ?: product.description,
            price = request.price ?: product.price,
            stockQuantity = request.stockQuantity ?: product.stockQuantity,
            imageUrl = request.imageUrl ?: product.imageUrl,
            category = category,
            isActive = request.isActive ?: product.isActive,
            createdAt = product.createdAt
        )

        val savedProduct = productRepository.save(updatedProduct)
        return savedProduct.toDto()
    }

    @Transactional
    fun deleteProduct(id: Long) {
        val product = productRepository.findById(id).orElseThrow { 
            NoSuchElementException("Product not found with id: $id") 
        }
        
        val deactivatedProduct = Product(
            id = product.id,
            name = product.name,
            description = product.description,
            price = product.price,
            stockQuantity = product.stockQuantity,
            imageUrl = product.imageUrl,
            category = product.category,
            isActive = false,
            createdAt = product.createdAt
        )
        
        productRepository.save(deactivatedProduct)
    }

    private fun Product.toDto(): ProductDto {
        return ProductDto(
            id = this.id,
            name = this.name,
            description = this.description,
            price = this.price,
            stockQuantity = this.stockQuantity,
            imageUrl = this.imageUrl,
            categoryName = this.category.name,
            isActive = this.isActive
        )
    }
}