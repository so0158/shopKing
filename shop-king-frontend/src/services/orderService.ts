import { api } from './api'
import type { Order, CreateOrderRequest } from '../types/index'

export interface OrdersResponse {
  content: Order[]
  totalElements: number
  totalPages: number
  size: number
  number: number
}

export const orderService = {
  async createOrder(orderData: CreateOrderRequest): Promise<Order> {
    const response = await api.post<Order>('/orders', orderData)
    return response.data
  },

  async getMyOrders(page = 0, size = 10): Promise<OrdersResponse> {
    const response = await api.get<OrdersResponse>('/orders/my-orders', {
      params: { page, size }
    })
    return response.data
  },

  async getOrderById(id: number): Promise<Order> {
    const response = await api.get<Order>(`/orders/${id}`)
    return response.data
  },
}