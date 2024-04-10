마이그레이션 이전 프로젝트 ver.1 : https://github.com/JayMon0327/MAT_ZIP
<hr>

# 🍕 맛집 migration
## [📚 프로젝트 작업일지](https://jaymon.notion.site/5c86177f92e649639e4fa40fa5befac1?pvs=4)

# [개인프로젝트] 맛.ZIP
💡해당 웹 사이트는 맛집 공유 및 추천을 목적으로 한 커뮤니티이며, 제품 판매 및 구매 기능을 제공합니다. 
기존의 스프링 레거시 프로젝트를 Spring Boot와 JPA 등 최신 기술로 성공적으로 마이그레이션하여 93% 성능 개선과 함께 
배포 프로세스 개선으로 CI/CD 무중단 배포를 이뤄냈습니다.

## [📑 API 명세](https://jaymon.notion.site/Mat-ZIP-API-9434785df71749fbbb7a1c6285f415a3?pvs=4)

<br>

## 1. 제작 기간
#### `2023년 11월 27일 ~ 1월 9일 (1개월)`

## 2. 배포 아키텍쳐 구조
#### `엘라스틱빈스톡 + RDS + ALB + NLB + IAM + GithubActions`
<img width="1377" alt="배포아키텍처" src="https://github.com/JayMon0327/JSP-SERVLET/assets/124110982/3e197aac-63a0-4e92-ac65-2c980115a9d7">

<br>

## 3. 성능테스트

## `기존 프로젝트와 성능 비교분석 결과`
## [📑 APACHE JMeter 부하테스트 측정](https://jaymon0327.tistory.com/6)
APACHE JMeter 측정 조건(프로젝트 각 12,000건)
* Number of Threads(users): 100
* Ramp-up period(sec): 1
* Loop Count: 30

<br>

  | 지표                    | 기존      | 현재    | 향상된 비율 (%)    |
  |-----------------------|---------|-------|------------------|
  | Response time (AVG/ms) | 2007ms  | 151ms | 92.48% 감소     |
  | Response time (Max/ms) | 13,184ms | 1,334ms | 89.88% 감소     |
  | TPS (sec)               | 13건    | 107건  | 723.08% 향상    |
  | Network (KB/sec)       | 69,583KB | 1,539KB | 97.79% 감소     |
  | Apdex                  | 0.002   | 0.974 | 48,600% 향상    |

<br>
<u>**기존 프로젝트**</u>
<img width="1793" alt="레거시프로젝트0410성능" src="https://github.com/JayMon0327/JSP-SERVLET/assets/124110982/eee0f9b9-0f2e-4827-b870-43d7b5571055">
<u>**현재 프로젝트**</u>
<img width="1791" alt="현재프로젝트0410성능" src="https://github.com/JayMon0327/JSP-SERVLET/assets/124110982/3cc53f14-9a97-4004-a04c-06d00b43b5c9">
<img width="1864" alt="스크린샷 2024-04-10 오후 3 04 25" src="https://github.com/JayMon0327/JSP-SERVLET/assets/124110982/7f89f3df-4493-4297-9984-ab7d080607f4">


<br>

## 4. 기능 구현
* 회원가입, 비밀번호 변경, 스프링 시큐리티 기반의 OAuth2 로그인(카카오, 네이버, 구글)
* 예외처리 시 사용자 정의 예외처리 에러코드 사용
* 전역 검증 핸들러를 작성하여 AOP를 이용한 공통 관심사 분리를 구현
* 상품 결제, 결제 취소, 결제내역 조회 (PortOne API)
* 상품 등록, 수정, 삭제
* 리뷰 등록, 삭제
* 파일 이미지 등록(MultipartFile)
* 재고 관리
* 상품 주문, 주문 취소 
* 배송 관리
* 포인트 적립, 포인트 사용

| <img src ="https://github.com/chujaeyeong/MAT_ZIP_readme_chujy/assets/123634960/97064c73-b97d-417d-9e33-54ca1a7a96b5" width="1150" height="750" /> |

<br>

## 5. 사용 기술
### `Back-end`
* Java 8 ➡️  Java 17
* Spring Framework 5 ➡️  Spring Boot 3.1.6
* Spring security 6
* Maven ➡️  Gradle
* Mybatis ➡️  JPA
* Spring Secutiry
* Spring Data JPA, QueryDSL

<br>

## 6. 주요 로직
<br>

## `결제 설계 구조`
<img width="1185" alt="스크린샷 2024-03-10 오후 10 09 10" src="https://github.com/JayMon0327/csStudy/assets/124110982/effb8cd9-fb66-4c35-bff7-a2ed749135ba">

<br>
<br>

## `스프링 시큐리티 구조`
<img width="1590" alt="스크린샷 2024-03-10 오후 10 21 04" src="https://github.com/JayMon0327/csStudy/assets/124110982/9ae91ba8-e243-48ef-a9e9-f05eb44b35b6">
<img width="1125" alt="스크린샷 2024-03-10 오후 10 21 17" src="https://github.com/JayMon0327/csStudy/assets/124110982/ef745b1e-8d75-4076-ac9d-b092bfacb66b">

<br>
<br>

## `Oauth 로그인 구조`
<img width="1087" alt="스크린샷 2024-03-20 오후 8 33 45" src="https://github.com/JayMon0327/JavaProject/assets/124110982/054ef104-414e-4f65-a161-c39daf8f43c4">
<img width="830" alt="스크린샷 2024-03-20 오후 9 22 42" src="https://github.com/JayMon0327/JavaProject/assets/124110982/02cf6e30-d188-4f04-b295-71805eb8df3e">

<br>
<br>

## `AOP를 이용한 공통 관심사 분리`
## [📑 AOP 전역처리](https://jaymon.notion.site/Valid-AOP-6d8e721bc4c54bbe8d9631da66a332ee?pvs=4)
<img width="765" alt="스크린샷 2024-03-20 오후 9 25 39" src="https://github.com/JayMon0327/JavaProject/assets/124110982/c57a03fd-4028-49d6-8b64-27d1682c6a84">
<img width="481" alt="스크린샷 2024-03-22 오후 6 43 54" src="https://github.com/JayMon0327/JSP-SERVLET/assets/124110982/9941a199-85a9-4453-8819-b80edcb68a3e">

<br>
<br>

## `ERD`
<img width="1318" alt="스크린샷 2024-03-10 오후 10 16 25" src="https://github.com/JayMon0327/csStudy/assets/124110982/99ca5e2b-9809-4217-8498-2a85fa6f819a">

<br>
<br>

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

**[상품 게시판]**
- [x] 상품 게시판
  - [x] 게시판 등록
    - [x] 게시판 등록, 수정, 삭제 테스트 코드 작성
    - [x] 게시판 등록 API를 위한 controller 코드 작성
    - [x] 게시판 등록 API를 위한 Service 코드 작성
    - [x] 요청 파라미터 값의 검증을 위한 DTO 생성
    - [x] 게시판 등록, 수정 폼 작성
    - [x] REST API 통신을 위한 ajax 작성

  - [x] 상품 등록
    - [x] 상품 등록, 수정, 삭제 테스트 코드 작성
    - [x] 게시판과 상품 정보 분리
    - [x] 게시판 엔티티와 상품 엔티티 연관관계 설정
    - [x] 상품 등록 API를 위한 controller 코드 작성
    - [x] 상품 등록 API를 위한 Service 코드 작성
    - [x] 요청 파라미터 값의 검증을 위한 DTO 생성
    - [x] 상품 등록, 수정 폼 작성
    - [x] REST API 통신을 위한 ajax 작성

  - [x] 이미지 등록
    - [x] 파일 업로드 테스트 코드 작성
    - [x] 파일 업로드 등록, 수정 서비스 코드 작성
    - [x] 상품과 사진의 양방향 연관관계를 연관관계 편의 메서드 작성
    - [x] 파일 업로드를 위한 등록, 수정 컨트롤러 코드와 DTO 작성
    - [x] 이미지 영속성 전이를 위한 상품 게시판 이미지 엔티티와 리뷰 이미지 엔티티 분리
    - [x] multipart/form-data 타입 전송을 위해 ajax 코드와 컨트롤러 코드 수정
    - [x] ajax 통신을 위한 Product 엔티티와 Item 엔티티의 래퍼 Dto 생성
    - [x] 코드 중복 제거를 위해 상품 등록과 수정 서비스 코드 모듈화
    - [x] 판매 정보 DTO 생성
    - [x] 상품 등록 통합 테스트 코드 작성
    - [x] 상품 등록 통합 서비스 코드 작성
    - [x] 상품 등록 통합 컨트롤러 코드 작성

  - [x] 게시판 조회  
    - [x] 상품 리스트 페이지
    - [x] 상품 상세 페이지
      - [x] 상세 페이지 뷰 반환을 위한 뷰 컨트롤러 작성
      - [x] 상세 페이지 ResponseDto 작성
      - [x] 이미지 출력을 위한 이미지 파일 URI 반환 메서드 작성
      - [x] 상세 페이지 템플릿 작성

- [x] 리뷰
  - [x] 리뷰 등록
    - [x] 리뷰 작성 테스트 코드 작성
    - [x] 리뷰 작성을 위한 서비스 코드 작성
    - [x] 파일 업로드를 위해 리뷰 이미지 파일명 변환 메서드 추가
    - [x] 리뷰 작성을 위한 request DTO 작성
    - [x] 리뷰 엔티티에 이미지 엔티티와 연관관계 편의 메서드 설정
    - [x] 리뷰 작성 ajax 코드 작성
    - [x] 리뷰 삭제

  - [x] 리뷰 리스트
    - [x] 상세 페이지 응답 DTO에 리뷰 엔티티와 리뷰 이미지 엔티티 관련 필드 추가
    - [x] 리뷰 조회를 위해 JSP 상세 페이지 수정


**[상품 주문]**

- [x] 주문 페이지 이동
  - [x] 주문 페이지에 전달할 Dto 작성
  - [x] 주문 페이지에 전달할 총 구매금액 로직 작성
  - [x] 주문 페이지에 배송정보, 총 구매금액을 ResponseDto로 전달 

- [x] 주문하기
  - [x] 주문 상태 관리
  - [x] 배달 상태 관리
  - [x] 재고 관리
  - [x] 최종 금액 계산
  - [x] 주문 생성
  - [x] 주문 취소


- [x] 포인트
  - [x] 구매 금액의 5% 적립
  - [x] 포인트 사용시 포인트 차감된 금액을 결제

**[상품 결제]**

- [x] 결제하기
  - [x] 결제 요청 페이지
  - [x] 결제 요청 페이지에 전달할 파라미터 정리
  - [x] 결제 API 통신 후 가격 검증 
  - [x] 포인트 계산 처리
  - [x] 결제 확인 및 주문 상태 처리

- [x] 결제 내역 조회
  - [x] 결제 내역 조회 페이징 처리

- [x] 결제 취소
  - [x] 취소 전 API 결제 조회로 결제 금액 검증
  - [x] 재고 원복, 주문 상태 변경
  - [x] 포인트 원복
  - [x] 결제 테이블에 취소 금액 저장
  - [x] 결제 취소 API로 환불처리
  - [x] 프론트 - 결제 조회 페이지에 결제 취소 버튼 추가와 ajax 작성




