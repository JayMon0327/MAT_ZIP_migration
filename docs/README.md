**[로그인 기능]**
### OAuth 2.0
- [ ] 스프링 시큐리티 적용
  - [ ] Security Config 생성
  - [ ] UserDetails 클래스 생성

- [ ] 유저 권한 설정
  - [ ] UserEntity 생성
  - [ ] Role Enum 생성
  - [ ] UserDetailsService 생성

- [ ] 기본 로그인 구현
  - [ ] UserController 생성
  - [ ] UserRepository에 회원찾기 로직 구현
  - [ ] UserDetails 클래스에 Auntentication 객체생성 로직 구현
  - [ ] loginForm 파일 생성

- [ ] 구글, 카카오, 네이버 로그인 구현
  - [ ] OAuth2설정 Secutiry Config에 추가
  - [ ] OAuth2UserService 생성
  - [ ] loadUser 메서드로 principal 객체 생성
  - [ ] OAuth2UserInfo 생성
  - [ ] 사용자 정보 응답 파라미터에 맞게 UserInfo 생성
  - [ ] OAuth 로그인 요청 함수 생성

- [ ] 미등록 사용자 자동 회원가입 구현


**[게시판]**
- [ ] 게시판
  - [ ] 게시판 글 생성
  - [ ] 게시판 글 수정
  - [ ] 게시판 글 삭제
  - [ ] 게시판 상세 페이지
  - [ ] 좋아요 기능
  - [ ] 조회수 기능

- [ ] 댓글
  - [ ] 댓글 등록
  - [ ] 댓글 수정
  - [ ] 댓글 리스트
  - [ ] 댓글 삭제

- [ ] 관리자 페이지 생성


**[차트]**
### Google Chart
- [ ] 매출 차트

- [ ] 재방문 차트
  - [ ] 이번달, 지난달 재방문율
  - [ ] 주문 횟수별 고객 수
  - [ ] 이번달, 지난달 신규 고객과 재방문 고객의 주문 총액

- [ ] 리뷰 감정분석 차트

**[결제]**
### Toss Payment
- [ ] 구독 이용자 중복체크
- [ ] 결제 하기
- [ ] 결제 후 회원 등록
- [ ] 결제 후 결제 내역 등록
- [ ] 결제 예외처리
