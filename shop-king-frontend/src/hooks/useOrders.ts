import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query'
import { orderService } from '../services/orderService'
import type { CreateOrderRequest } from '../types/index'
import { useCartStore } from '../store/cartStore'

export const useOrders = (page = 0, size = 10) => {
  return useQuery({
    queryKey: ['orders', page, size],
    queryFn: () => orderService.getMyOrders(page, size),
    staleTime: 2 * 60 * 1000,
  })
}

export const useOrder = (id: number) => {
  return useQuery({
    queryKey: ['order', id],
    queryFn: () => orderService.getOrderById(id),
    enabled: !!id,
    staleTime: 2 * 60 * 1000,
  })
}

export const useCreateOrder = () => {
  const queryClient = useQueryClient()
  const clearCart = useCartStore(state => state.clearCart)

  return useMutation({
    mutationFn: (orderData: CreateOrderRequest) => orderService.createOrder(orderData),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['orders'] })
      clearCart()
    },
  })
}