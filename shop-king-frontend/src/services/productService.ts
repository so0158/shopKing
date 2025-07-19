import { api } from './api'
import type { Product, Category } from '../types/index'

export interface ProductsResponse {
  content: Product[]
  totalElements: number
  totalPages: number
  size: number
  number: number
}

export const productService = {
  async getProducts(params: {
    page?: number
    size?: number
    sortBy?: string
    sortDir?: string
    categoryId?: number
    name?: string
  } = {}): Promise<ProductsResponse> {
    const response = await api.get<ProductsResponse>('/products', { params })
    return response.data
  },

  async getProductById(id: number): Promise<Product> {
    const response = await api.get<Product>(`/products/${id}`)
    return response.data
  },

  async getCategories(): Promise<Category[]> {
    const response = await api.get<Category[]>('/categories')
    return response.data
  },

  async searchProducts(name: string, page = 0, size = 10): Promise<ProductsResponse> {
    const response = await api.get<ProductsResponse>('/products/search', {
      params: { name, page, size }
    })
    return response.data
  },

  async getProductsByCategory(categoryId: number, page = 0, size = 10): Promise<ProductsResponse> {
    const response = await api.get<ProductsResponse>(`/products/category/${categoryId}`, {
      params: { page, size }
    })
    return response.data
  },
}