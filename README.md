# 맛집 migration 
## [📚 프로젝트 작업일지](https://jaymon.notion.site/5c86177f92e649639e4fa40fa5befac1?pvs=4)
### `Back-end`
* Java 8 ➡️  Java 17
* Spring Framework 5 ➡️  Spring Boot 3.1.6
* Maven ➡️  Gradle
* Mybatis ➡️  JPA

**[추가]**
* Spring Secutiry
* Spring Data JPA, QueryDSL

**[추가 : 로그인 기능]**
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

- [ ] 회원가입 
  - [x] 회원가입 폼을 위한 회원가입 DTO 정의
  - [x] 회원가입 API를 위한 controller 코드 작성과 프론트 회원가입 폼 작성
  - [x] 요청 파라미터 값의 검증을 위한 검증과 공통처리
  - [x] 예외 모니터링을 위한 사용자 정의 예외 클래스 생성과 예외 처리
  - [ ] 회원 가입 테스트 코드 작성

**[차트]**
### Google Chart
- [ ] 매출 차트
  - [ ] 매출차트를 막대 그래프로 변경
  - [ ] 매출에 null값도 허용되도록 수정
  - [ ] 프론트의 데이터 계산 로직 서버로 분리

- [ ] 재방문 차트
  - [ ] 이번달, 지난달 재방문율
  - [ ] 주문 횟수별 고객 수
  - [ ] 이번달, 지난달 신규 고객과 재방문 고객의 주문 총액

- [ ] 리뷰 감정분석 차트

**[커뮤니티 게시판 ➡️ 음식점 게시판]**
- [ ] 게시판
    - [ ] 게시판 글 등록
    - [ ] 파일 업로드
    - [ ] 게시판 글 수정
    - [ ] 게시판 글 삭제
    - [ ] 음식점 상세 페이지
    - [ ] 찜(좋아요) 기능
    - [ ] 조회수(매장순위 정렬) 기능
    - [ ] 음식점 검색 기능

- [ ] 리뷰
    - [ ] 리뷰 등록
    - [ ] 리뷰 리스트
    - [ ] 리뷰 수정
    - [ ] 리뷰 삭제

- [ ] 관리자 페이지 생성
  - [ ] 관리자 1:1 문의


**[구독결제 ➡️ 음식 주문 결제]**
### Toss Payment
- [ ] 포인트 충전
  - [ ] 포인트 계산 로직 작성
  - [ ] 포인트 결제 예외처리

- [ ] 음식 주문하기
  - [ ] 장바구니 구현
  - [ ] 결제 후 주문 테이블 등록
  - [ ] 음식 주문결제 예외처리

