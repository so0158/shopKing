import { useQuery, useInfiniteQuery } from '@tanstack/react-query'
import { productService } from '../services/productService'

export const useProducts = (params: {
  page?: number
  size?: number
  sortBy?: string
  sortDir?: string
  categoryId?: number
  name?: string
} = {}) => {
  return useQuery({
    queryKey: ['products', params],
    queryFn: () => productService.getProducts(params),
    staleTime: 5 * 60 * 1000,
  })
}

export const useInfiniteProducts = (params: {
  size?: number
  sortBy?: string
  sortDir?: string
  categoryId?: number
  name?: string
} = {}) => {
  return useInfiniteQuery({
    queryKey: ['products', 'infinite', params],
    queryFn: ({ pageParam = 0 }) =>
      productService.getProducts({ ...params, page: pageParam }),
    getNextPageParam: (lastPage) => {
      if (lastPage.number < lastPage.totalPages - 1) {
        return lastPage.number + 1
      }
      return undefined
    },
    initialPageParam: 0,
    staleTime: 5 * 60 * 1000,
  })
}

export const useProduct = (id: number) => {
  return useQuery({
    queryKey: ['product', id],
    queryFn: () => productService.getProductById(id),
    enabled: !!id,
    staleTime: 5 * 60 * 1000,
  })
}

export const useCategories = () => {
  return useQuery({
    queryKey: ['categories'],
    queryFn: async () => {
      const result = await productService.getCategories()
      console.log('Categories API response:', result, 'Type:', typeof result, 'IsArray:', Array.isArray(result))
      return result
    },
    staleTime: 10 * 60 * 1000,
  })
}

export const useSearchProducts = (query: string, enabled = true) => {
  return useQuery({
    queryKey: ['products', 'search', query],
    queryFn: () => productService.searchProducts(query),
    enabled: enabled && query.length > 2,
    staleTime: 5 * 60 * 1000,
  })
}