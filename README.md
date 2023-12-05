# 🍕 맛집 migration
## [📚 프로젝트 작업일지](https://jaymon.notion.site/5c86177f92e649639e4fa40fa5befac1?pvs=4)

# 사장님들을 위한 커뮤니티 서비스 ➡️ 판매자들을 위한 쇼핑몰
* 판매 분석을 위한 매출차트
* 스프링 시큐리티 기반의 OAuth2 로그인
* 상품 등록, 재고 관리
* 주문 취소와 배송 관리
* 포인트 적립
* 결제하기

### `Back-end`
* Java 8 ➡️  Java 17
* Spring Framework 5 ➡️  Spring Boot 3.1.6
* Maven ➡️  Gradle
* Mybatis ➡️  JPA

**[추가]**
* Spring Secutiry
* Spring Data JPA, QueryDSL

<hr>

# Docs 문서

**[로그인]**
### OAuth 2.0
- [x] 스프링 시큐리티 적용
    - [x] Security Config 생성
    - [x] UserDetails 클래스 생성

- [x] 유저 권한 설정
    - [x] UserEntity 생성
    - [x] 유저 권한 설정을 위한 Role Enum 생성
    - [x] UserDetailsService 생성

- [x] 기본 로그인 구현
    - [x] UserRepository에 회원찾기 로직 구현
    - [x] UserController 생성
    - [x] loginForm 파일 생성

- [x] 구글, 카카오, 네이버, 페이스북 로그인 구현
    - [x] OAuth2설정 Secutiry Config에 추가
    - [x] OAuth2UserService에 loadUser 메서드로 principal 객체 생성
    - [x] OAuth유저 정보 조회를 위한 UserInfo 생성
    - [x] 구글, 카카오, 네이버, 페이스북 사용자 정보 응답 파라미터에 맞게 UserInfo 생성
    - [x] OAuth userDetails 클래스에 OAuth 로그인 요청 함수 생성
    - [x] loginForm에 소셜 로그인 버튼 생성
    - [x] 미등록 사용자 자동 회원가입 구현

- [x] 회원가입 
  - [x] 회원가입 테스트 코드 작성
  - [x] 회원가입 폼을 위한 회원가입 DTO 정의
  - [x] 회원가입 API를 위한 controller 코드 작성과 프론트 회원가입 폼 작성
  - [x] 요청 파라미터 값의 검증과 공통처리
  - [x] 예외 모니터링을 위한 사용자 정의 예외 클래스 생성과 예외 처리
  - [x] 회원가입 비밀번호 검증과 예외처리 로직 추가

- [x] 회원수정
  - [x] 회원 수정 테스트 코드 작성
  - [x] 회원 수정을 위한 폼 생성
  - [x] 컨트롤러 API 응답을 위한 DTO 생성, 예외 핸들러와 역할 분리
  - [x] 회원 권한 세팅을 위해 스프링 시큐리티 설정 파일 수정
  - [x] 회원 수정 더티체킹을 위한 메서드 생성
  - [x] 회원 수정 로직 구현과 예외처리를 위한 오류코드 추가

**[커뮤니티 게시판 ➡️ 판매 스토어]**
- [ ] 상품 게시판
  - [x] 상품 등록, 수정, 삭제 테스트 코드 작성
  - [x] 스프링 시큐리티 권한 설정에 SELLER 추가
  - [x] 상품 등록 API를 위한 controller 코드 작성
  - [x] 상품 등록 API를 위한 Service 코드 작성
  - [x] 요청 파라미터 값의 검증을 위한 DTO 생성
  - [x] 상품 등록, 수정 폼 작성
  - [ ] 파일 업로드 구현
  - [ ] 상품 상세 페이지
  - [ ] 상품 리스트
  - [ ] 상품 검색 기능

- [ ] 리뷰
  - [ ] 리뷰 등록
  - [ ] 리뷰 리스트
  - [ ] 리뷰 수정
  - [ ] 리뷰 삭제

- [ ] 상품 관리
  - [ ] 주문 상태 관리
  - [ ] 배달 상태 관리
  - [ ] 재고 관리


- [ ] 관리자 페이지 생성
  - [ ] 관리자 1:1 문의


**[구독 결제 ➡️ 상품 결제]**
### Toss Payment
- [ ] 포인트
  - [ ] 토스 페이로 충전 가능
  - [ ] 구매 금액의 5% 적립
  - [ ] 포인트로 결제 가능

- [ ] 결제
  - [ ] 장바구니
  - [ ] 결제 입력 페이지
  - [ ] 토스페이와 포인트로 결제
  - [ ] 결제 API와 포인트의 예외처리
  - [ ] 주문 테이블 등록
  - [ ] 결제 내역 등록
  - [ ] 결제 내역 조회


**[차트]**
### Google Chart
- [ ] 매출 차트
  - [ ] Payment 테이블 JPA 엔티티로 구현
  - [ ] MyBatis 쿼리를 JPQL과 QueryDSL로 구현
  - [ ] 엔티티 그래프와 패치조인으로 성능 향상
  - [ ] 프론트의 데이터 계산 로직 서버로 분리
  - [ ] 구글 차트를 막대 그래프로 변경

- [ ] 재방문 차트
  - [ ] 이번달, 지난달 재방문율
    - [ ] Payment 테이블 JPA 엔티티로 구현
    - [ ] MyBatis 쿼리를 JPQL과 QueryDSL로 구현
    - [ ] 엔티티 그래프와 패치조인으로 성능 향상

  - [ ] 주문 횟수별 고객 수
    - [ ] Payment 테이블 JPA 엔티티로 구현
    - [ ] MyBatis 쿼리를 JPQL과 QueryDSL로 구현
    - [ ] 엔티티 그래프와 패치조인으로 성능 향상

  - [ ] 이번달, 지난달 신규 고객과 재방문 고객의 주문 총액
    - [ ] Payment 테이블 JPA 엔티티로 구현
    - [ ] MyBatis 쿼리를 JPQL과 QueryDSL로 구현
    - [ ] 엔티티 그래프와 패치조인으로 성능 향상

- [ ] 리뷰 감정분석 차트
  - [ ] Payment 테이블 JPA 엔티티로 구현
  - [ ] MyBatis 쿼리를 JPQL과 QueryDSL로 구현
  - [ ] 엔티티 그래프와 패치조인으로 성능 향상


