-- 데이터베이스 초기화 스크립트
-- shop_king 데이터베이스와 사용자는 이미 환경변수로 생성됨

-- 기본 카테고리 데이터 추가
INSERT INTO categories (name, description) VALUES 
('전자제품', '컴퓨터, 스마트폰, 가전제품 등'),
('의류', '남성복, 여성복, 아동복 등'),
('도서', '소설, 전문서적, 만화 등'),
('스포츠', '운동용품, 스포츠웨어 등'),
('홈&리빙', '가구, 인테리어 소품 등')
ON CONFLICT DO NOTHING;

-- 관리자 계정 생성 (비밀번호: admin123)
INSERT INTO users (email, password, name, phone_number, address, role, created_at, updated_at) VALUES 
('admin@shopking.com', '$2a$10$DowJonesIndexVerySecureHashedPassword', '관리자', '010-1234-5678', '서울시 강남구', 'ADMIN', NOW(), NOW())
ON CONFLICT (email) DO NOTHING;