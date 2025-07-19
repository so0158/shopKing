# ShopKing Frontend

React 18과 최신 기술 스택을 활용한 현대적인 쇼핑몰 프론트엔드 애플리케이션입니다.

## 🛠️ 기술 스택

### 핵심 프레임워크
- **React 18** - 최신 React 기능 활용
- **TypeScript** - 타입 안정성
- **Vite** - 빠른 개발 서버 및 빌드

### 스타일링
- **Tailwind CSS** - 유틸리티 퍼스트 CSS 프레임워크
- **ShadCN UI** - 재사용 가능한 컴포넌트 라이브러리
- **Headless UI** - 접근성이 고려된 UI 컴포넌트
- **Heroicons** - 아이콘 라이브러리

### 상태 관리
- **Zustand** - 경량 상태 관리 라이브러리
- **React Query** - 서버 상태 관리 및 캐싱

### 폼 및 유효성 검사
- **React Hook Form** - 성능 최적화된 폼 라이브러리
- **Zod** - TypeScript 스키마 유효성 검사

### 라우팅 및 HTTP
- **React Router** - 클라이언트 사이드 라우팅
- **Axios** - HTTP 클라이언트

### 개발 도구
- **ESLint** - 코드 품질 검사
- **Prettier** - 코드 포매팅

## 📁 프로젝트 구조

```
src/
├── components/          # 재사용 가능한 컴포넌트
│   ├── ui/             # 기본 UI 컴포넌트 (ShadCN)
│   ├── layout/         # 레이아웃 컴포넌트
│   └── forms/          # 폼 컴포넌트
├── pages/              # 페이지 컴포넌트
├── hooks/              # 커스텀 훅
├── store/              # Zustand 스토어
├── services/           # API 서비스
├── types/              # TypeScript 타입 정의
├── lib/                # 유틸리티 함수
└── utils/              # 헬퍼 함수
```

## 🚀 주요 기능

### 사용자 인증
- JWT 기반 인증 시스템
- 로그인/회원가입 폼 (유효성 검사 포함)
- 자동 토큰 갱신 및 인증 상태 관리

### 상품 관리
- 상품 리스트 및 상세 정보
- 카테고리별 필터링
- 상품 검색 기능
- 무한 스크롤 및 페이지네이션

### 장바구니
- 실시간 장바구니 상태 관리
- 상품 수량 조절
- 로컬 스토리지 연동으로 데이터 지속성

### UI/UX
- 반응형 디자인
- 다크/라이트 모드 지원
- 로딩 및 에러 상태 처리
- 접근성 고려된 컴포넌트

## 🛠️ 설치 및 실행

### 1. 의존성 설치
```bash
npm install
```

### 2. 환경 변수 설정
```bash
cp .env.example .env
```

`.env` 파일에서 백엔드 API URL을 설정하세요:
```
VITE_API_BASE_URL=http://localhost:8080/api
```

### 3. 개발 서버 실행
```bash
npm run dev
```

### 4. 빌드
```bash
npm run build
```

## 🎯 상태 관리 아키텍처

### Zustand 스토어
- **authStore**: 사용자 인증 상태
- **cartStore**: 장바구니 상태

### React Query
- 서버 데이터 캐싱 및 동기화
- API 요청 상태 관리 (로딩, 에러, 성공)
- 백그라운드 데이터 재검증

## 🔗 API 통신

### HTTP 클라이언트 설정
- Axios 인터셉터로 자동 토큰 첨부
- 401 에러 시 자동 로그아웃
- API 응답 타입 정의

### 주요 서비스
- **authService**: 인증 관련 API
- **productService**: 상품 관련 API  
- **orderService**: 주문 관련 API

## 🎨 디자인 시스템

### Tailwind CSS 설정
- 커스텀 컬러 팔레트
- 반응형 그리드 시스템
- 다크 모드 지원

### ShadCN UI 컴포넌트
- 일관된 디자인 언어
- 접근성 최적화
- 커스터마이징 가능

## 📱 반응형 디자인

- **Mobile First** 접근법
- 브레이크포인트: `sm`, `md`, `lg`, `xl`, `2xl`
- 터치 친화적 인터페이스

## 🔒 보안

- XSS 방지를 위한 입력 검증
- JWT 토큰 보안 저장
- API 요청 인증 헤더 자동 관리

## 🧪 개발 팁

### 커스텀 훅 사용
```tsx
// 상품 데이터 가져오기
const { data: products, isLoading } = useProducts()

// 인증 상태 관리
const { user, login, logout } = useAuth()

// 장바구니 상태
const { items, addItem, removeItem } = useCartStore()
```

### 폼 유효성 검사
```tsx
const schema = z.object({
  email: z.string().email(),
  password: z.string().min(6),
})

const { register, handleSubmit, formState: { errors } } = useForm({
  resolver: zodResolver(schema)
})
```

## 🤝 기여하기

1. 이 레포지토리를 포크하세요
2. feature 브랜치를 생성하세요 (`git checkout -b feature/amazing-feature`)
3. 변경사항을 커밋하세요 (`git commit -m 'Add amazing feature'`)
4. 브랜치에 푸시하세요 (`git push origin feature/amazing-feature`)
5. Pull Request를 생성하세요

## 📄 라이선스

이 프로젝트는 MIT 라이선스 하에 배포됩니다.