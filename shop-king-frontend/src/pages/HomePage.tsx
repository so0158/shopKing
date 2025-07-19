import React from 'react'
import { Link } from 'react-router-dom'
import { Button } from '../components/ui/button'
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '../components/ui/card'
import { useProducts, useCategories } from '../hooks/useProducts'
import { formatPrice } from '../lib/utils'

export const HomePage: React.FC = () => {
  const { data: productsData, isLoading: productsLoading } = useProducts({ size: 8 })
  const { data: categories, isLoading: categoriesLoading } = useCategories()

  return (
    <div className="container py-8">
      {/* Hero Section */}
      <section className="relative py-12 lg:py-20">
        <div className="text-center">
          <h1 className="text-4xl font-bold tracking-tight sm:text-6xl">
            ShopKing에 오신 것을 환영합니다
          </h1>
          <p className="mt-6 text-lg leading-8 text-muted-foreground">
            최고의 상품을 최적의 가격으로 만나보세요
          </p>
          <div className="mt-10 flex items-center justify-center gap-x-6">
            <Button asChild size="lg">
              <Link to="/products">쇼핑 시작하기</Link>
            </Button>
            <Button variant="outline" asChild size="lg">
              <Link to="/categories">카테고리 보기</Link>
            </Button>
          </div>
        </div>
      </section>

      {/* Categories Section */}
      <section className="py-12">
        <div className="flex items-center justify-between mb-8">
          <h2 className="text-3xl font-bold tracking-tight">인기 카테고리</h2>
          <Button variant="outline" asChild>
            <Link to="/categories">모든 카테고리</Link>
          </Button>
        </div>
        
        {categoriesLoading ? (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
            {[...Array(4)].map((_, i) => (
              <div key={i} className="h-32 bg-muted animate-pulse rounded-lg" />
            ))}
          </div>
        ) : (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
            {Array.isArray(categories) && categories.slice(0, 4).map((category) => (
              <Card key={category.id} className="hover:shadow-lg transition-shadow cursor-pointer">
                <Link to={`/products?category=${category.id}`}>
                  <CardHeader className="text-center">
                    <CardTitle className="text-lg">{category.name}</CardTitle>
                    <CardDescription>{category.description}</CardDescription>
                  </CardHeader>
                </Link>
              </Card>
            ))}
          </div>
        )}
      </section>

      {/* Featured Products Section */}
      <section className="py-12">
        <div className="flex items-center justify-between mb-8">
          <h2 className="text-3xl font-bold tracking-tight">추천 상품</h2>
          <Button variant="outline" asChild>
            <Link to="/products">모든 상품</Link>
          </Button>
        </div>

        {productsLoading ? (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
            {[...Array(8)].map((_, i) => (
              <div key={i} className="h-80 bg-muted animate-pulse rounded-lg" />
            ))}
          </div>
        ) : (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
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
                  <CardHeader>
                    <CardTitle className="text-lg truncate">{product.name}</CardTitle>
                    <CardDescription className="line-clamp-2">
                      {product.description}
                    </CardDescription>
                  </CardHeader>
                  <CardContent>
                    <div className="flex items-center justify-between">
                      <span className="text-2xl font-bold text-primary">
                        {formatPrice(product.price)}
                      </span>
                      <span className="text-sm text-muted-foreground">
                        재고: {product.stockQuantity}
                      </span>
                    </div>
                  </CardContent>
                </Link>
              </Card>
            ))}
          </div>
        )}
      </section>
    </div>
  )
}