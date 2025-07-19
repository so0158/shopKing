import React from 'react'
import { Link, useSearchParams } from 'react-router-dom'
import { Button } from '../components/ui/button'
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '../components/ui/card'
import { Input } from '../components/ui/input'
import { useProducts, useCategories } from '../hooks/useProducts'
import { useCartStore } from '../store/cartStore'
import { formatPrice } from '../lib/utils'
import { ShoppingCartIcon } from '@heroicons/react/24/outline'

export const ProductsPage: React.FC = () => {
  const [searchParams, setSearchParams] = useSearchParams()
  const [localSearch, setLocalSearch] = React.useState(searchParams.get('search') || '')
  
  const page = parseInt(searchParams.get('page') || '0')
  const categoryId = searchParams.get('category') ? parseInt(searchParams.get('category')!) : undefined
  const search = searchParams.get('search') || undefined

  const { data: productsData, isLoading } = useProducts({
    page,
    size: 12,
    categoryId,
    name: search,
  })
  
  const { data: categories } = useCategories()
  const addToCart = useCartStore(state => state.addItem)

  const handleSearch = (e: React.FormEvent) => {
    e.preventDefault()
    const newParams = new URLSearchParams(searchParams)
    if (localSearch.trim()) {
      newParams.set('search', localSearch.trim())
    } else {
      newParams.delete('search')
    }
    newParams.delete('page')
    setSearchParams(newParams)
  }

  const handleCategoryFilter = (catId: number | null) => {
    const newParams = new URLSearchParams(searchParams)
    if (catId) {
      newParams.set('category', catId.toString())
    } else {
      newParams.delete('category')
    }
    newParams.delete('page')
    setSearchParams(newParams)
  }

  const handlePageChange = (newPage: number) => {
    const newParams = new URLSearchParams(searchParams)
    newParams.set('page', newPage.toString())
    setSearchParams(newParams)
  }

  const handleAddToCart = (product: any) => {
    addToCart(product, 1)
  }

  return (
    <div className="container py-8">
      <div className="flex flex-col lg:flex-row gap-8">
        {/* Sidebar Filters */}
        <aside className="lg:w-64 space-y-6">
          <div>
            <h3 className="font-semibold mb-4">검색</h3>
            <form onSubmit={handleSearch}>
              <Input
                placeholder="상품명 검색..."
                value={localSearch}
                onChange={(e) => setLocalSearch(e.target.value)}
              />
            </form>
          </div>

          <div>
            <h3 className="font-semibold mb-4">카테고리</h3>
            <div className="space-y-2">
              <Button
                variant={!categoryId ? "default" : "ghost"}
                className="w-full justify-start"
                onClick={() => handleCategoryFilter(null)}
              >
                전체
              </Button>
              {categories?.map((category) => (
                <Button
                  key={category.id}
                  variant={categoryId === category.id ? "default" : "ghost"}
                  className="w-full justify-start"
                  onClick={() => handleCategoryFilter(category.id)}
                >
                  {category.name}
                </Button>
              ))}
            </div>
          </div>
        </aside>

        {/* Main Content */}
        <main className="flex-1">
          <div className="mb-6">
            <h1 className="text-3xl font-bold tracking-tight">상품 목록</h1>
            {search && (
              <p className="text-muted-foreground mt-2">
                "{search}" 검색 결과: {productsData?.totalElements || 0}개
              </p>
            )}
          </div>

          {isLoading ? (
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
              {[...Array(12)].map((_, i) => (
                <div key={i} className="h-80 bg-muted animate-pulse rounded-lg" />
              ))}
            </div>
          ) : (
            <>
              <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                {productsData?.content?.map((product) => (
                  <Card key={product.id} className="hover:shadow-lg transition-shadow">
                    <Link to={`/products/${product.id}`}>
                      <div className="aspect-square bg-muted rounded-t-lg flex items-center justify-center">
                        {product.imageUrl ? (
                          <img
                            src={product.imageUrl}
                            alt={product.name}
                            className="w-full h-full object-cover rounded-t-lg"
                          />
                        ) : (
                          <span className="text-muted-foreground">이미지 없음</span>
                        )}
                      </div>
                    </Link>
                    <CardHeader>
                      <CardTitle className="text-lg truncate">
                        <Link to={`/products/${product.id}`} className="hover:text-primary">
                          {product.name}
                        </Link>
                      </CardTitle>
                      <CardDescription className="line-clamp-2">
                        {product.description}
                      </CardDescription>
                    </CardHeader>
                    <CardContent>
                      <div className="flex items-center justify-between mb-3">
                        <span className="text-2xl font-bold text-primary">
                          {formatPrice(product.price)}
                        </span>
                        <span className="text-sm text-muted-foreground">
                          재고: {product.stockQuantity}
                        </span>
                      </div>
                      <Button
                        className="w-full"
                        onClick={() => handleAddToCart(product)}
                        disabled={product.stockQuantity === 0}
                      >
                        <ShoppingCartIcon className="w-4 h-4 mr-2" />
                        장바구니 담기
                      </Button>
                    </CardContent>
                  </Card>
                ))}
              </div>

              {/* Pagination */}
              {productsData && productsData.totalPages > 1 && (
                <div className="flex justify-center items-center space-x-2 mt-8">
                  <Button
                    variant="outline"
                    onClick={() => handlePageChange(page - 1)}
                    disabled={page === 0}
                  >
                    이전
                  </Button>
                  
                  <span className="text-sm text-muted-foreground">
                    {page + 1} / {productsData.totalPages}
                  </span>
                  
                  <Button
                    variant="outline"
                    onClick={() => handlePageChange(page + 1)}
                    disabled={page >= productsData.totalPages - 1}
                  >
                    다음
                  </Button>
                </div>
              )}
            </>
          )}
        </main>
      </div>
    </div>
  )
}