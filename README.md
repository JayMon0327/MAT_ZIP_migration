마이그레이션 이전 프로젝트 ver.1 : https://github.com/JayMon0327/MAT_ZIP
<hr>

# 🍕 맛집 migration
## [📚 프로젝트 작업일지](https://jaymon.notion.site/5c86177f92e649639e4fa40fa5befac1?pvs=4)

# [개인프로젝트] 맛.ZIP
💡해당 웹 사이트는 맛집 공유 및 추천을 목적으로 한 커뮤니티이며, 기존 고객 관점의 맛집 플랫폼을 요식업 사장님들의 관점으로 풀어낸 사이트입니다. 제품 판매 및 구매 기능을 제공합니다. 
기존의 스프링 레거시와 Mybatis로 구성된 프로젝트를 Spring Boot와 JPA 등 최신 기술로 성공적으로 마이그레이션하여 93% 성능 개선과 함께 
배포 프로세스 개선으로 CI/CD 무중단 배포를 이뤄냈습니다.

## [📑 API 명세](https://jaymon.notion.site/Mat-ZIP-API-9434785df71749fbbb7a1c6285f415a3?pvs=4)

<br>

## 1. 제작 기간
#### `2023년 11월 27일 ~ 2월 9일`

<br>
<br>

## 2. 배포 아키텍쳐 구조
#### `엘라스틱빈스톡 + RDS + ALB + NLB + IAM + GithubActions`
<img width="1377" alt="배포아키텍처" src="https://github.com/JayMon0327/SpringStudy/assets/124110982/0640f5c6-1892-4994-bd5b-f9f932961eff">

<br>
<br>

* GitHub Actions, Elastic Beanstalk, RDS, ALB, NLB, IAM 사용
* 무중단 배포를 위해 로드밸런싱 전략으로 블루/그린 배포 전략 도입(인스턴스 최소 2/ 최대 4)
* 고정된 IP주소 확보와 외부 접근으로부터 보안성을 위해 NLB를 추가하여 ElasticIP를 할당하고 리스너, 포워딩 규칙 설정
* API키 보안과 RDS 접속보안을 위해 Github Actions 워크플로우로 환경변수 관리

<br>
<br>

## 3. 성능테스트

## `기존 프로젝트와 성능 비교분석 결과`
## [📑 APACHE JMeter 부하테스트 측정](https://jaymon0327.tistory.com/7)
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

**기존 프로젝트**
<img width="1793" alt="기존성능" src="https://github.com/JayMon0327/SpringStudy/assets/124110982/2244709f-d097-4b14-8a09-1d0610fac1d5">

<br>

**현재 프로젝트**
<img width="1791" alt="현재성능" src="https://github.com/JayMon0327/SpringStudy/assets/124110982/325e5ecd-de44-41da-b6ff-328ce8fe14ee">
<img width="1864" alt="성능그래프" src="https://github.com/JayMon0327/SpringStudy/assets/124110982/7789d094-9b3d-4f41-baa0-f39bd159b8cf">

<br>

### 성능 향상 요인 분석

<details>
  <summary>📌성능 향상 요인 분석</summary>

##### `1. JPA 캐싱 메커니즘과 쿼리 자동 최적화`
* JPA는 1차 캐시(영속성 컨텍스트 내부 캐시)와 2차 캐시(응용 프로그램 범위의 캐시)를 활용하여 반복적인 데이터 조회 요청 시 데이터베이스 접근을 최소화합니다. 이를 위해 JPQL을 사용하여 쿼리를 자동 최적화하고, Fetch join을 통해 N+1문제를 해결하면서 관련 엔티티를 효율적으로 로딩하여 데이터 베이스 조회 성능을 극대화했습니다.
  응답 시간이 92% 개선되고 전체 처리량(TPS)이 723% 향상 되었습니다.

##### `2.  AOP를 통한 예외 처리와 코드 최적화`
* AOP를 활용하여 API 요청과 응답 시 공통된 부분들의 예외 처리 및 검증을 중앙에서 관리함으로써 코드의 복잡성을 줄이고 유지보수를 용이하게 했습니다. 불필요한 코드 제거로 프로세스를 경량화하여 성능을 향상시켰습니다.

##### `3. API 통신 시 DTO 패턴 사용`
* 데이터 전송 객체(Data Transfer Object) 패턴을 사용하여 필요한 데이터만을 선별해 전송함으로써 데이터 오버헤드를 줄이고 네트워크 부하를 97% 감소시켰습니다.

##### `4. JPA의 자동 트랜잭션 관리`
* JPA는 트랜잭션 관리를 자동화하여, 개발자가 직접 트랜잭션 코드를 작성할 필요 없이 효율적인 데이터 처리가 가능하게 합니다. 이로 인해 불필요한 트랜잭션 코드 제거와 함께 데이터의 일관성을 보장하고 실행 효율이 크게 개선되었습니다.

##### `5. Maven에서 Gradle로의 빌드 시스템 전환`
* Maven에서 Gradle로 빌드 시스템을 전환하여 빌드 과정의 속도와 효율성을 향상시켰습니다. Gradle은 선언적인 빌드 구성과 함께 캐싱과 병렬 처리 기능을 제공하여 빌드 시간을 단축시키는 등 간접적으로 성능에 기여했습니다.

##### `6. Thymeleaf를 사용한 뷰와 비즈니스 로직의 분리`
* 소스코드가 섞여있던 JSP에서 타임리프로의 전환을 통해, 뷰와 비즈니스 로직을 명확히 분리함으로써 MVC 모델의 원칙을 강화했습니다. 이는 유지보수성과 개발 효율성을 높이고, 서버 리소스의 효율적 사용을 가능하게 해 간접적으로 성능이 향상되었습니다.

##### `7. Ajax를 사용한 비동기 API 통신과 JSON 데이터 교환`
* 설계한 모든 API 통신에 Ajax를 활용하고 JSON 데이터 형식으로만 통신함으로써 데이터 경량화와 교환의 효율성을 높이고, RESTful API 설계 원칙에 따라 API를 설계하면서 시스템 간의 호환성을 강화했습니다.

</details>

<br>
<br>

## 4. 기능 구현
* 스프링 시큐리티 기반의 OAuth2 로그인(카카오, 네이버, 구글, 페이스북), 회원가입, 비밀번호 변경
* 상품 결제 시스템, 결제 취소, 결제내역 조회 (PortOne API - (구)아임포트)
* 재고 관리, 배송관리 시스템
* 포인트 적립 시스템
* 쇼핑몰 게시판 CRUD RESTful API(Json 통합)
* 리뷰 등록, 삭제, 별점 부여
* 파일 이미지 등록(MultipartFile)
* 예외처리 시 사용자 정의 예외처리 에러코드 사용

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
* Spring Data JPA

<br>

## 6. 주요 로직

## `Oauth 로그인 구조`
<img width="1087" alt="로그인구조1" src="https://github.com/JayMon0327/SpringStudy/assets/124110982/2dcccfaf-a3ad-4291-ba43-79d0984ca7ed">
<img width="830" alt="로그인구조2" src="https://github.com/JayMon0327/SpringStudy/assets/124110982/177c7aed-ed7b-4d50-b131-3a4c0c86678c">

<br>
<br>

* Oauth 로그인 시 미가입 사용자를 구분하여 자동 회원가입 진행(플랫폼 정보와 Oauth서버의 이메일 주소를 이용, 가입 시 일반 유저 등급 부여)
* 추후 유연하게 타 플랫폼 추가 확장을 위해 인터페이스로 OAuth 플랫폼들을 관리
* YML 파일을 이용하여 Redirect URI와 API키를 중앙에서 관리
* 배포 시에는 API키 보안을 위해 GIT Actions Repository secrets로 시크릿키를 관리

<br>
<br>

## `결제 설계 구조`
<img width="1185" alt="결제구조" src="https://github.com/JayMon0327/SpringStudy/assets/124110982/d0c644d5-6ef6-4f25-8b77-ba6ffe61eff6">

<br>
<br>

* 4단계에 걸쳐 검증진행(재고 검증 -> 액세스 토큰 발급 -> 결제 조회 API 요청을 통한 가격 재검증 -> 포인트 검증)
* 잘못된 주문을 방지하기 위해 주문 상태를 TRY와 ORDER로 나눠서 관리
* 보안성 강화를 위해 사용자에게 반환할 때는 새로운 객체를 만들어 반환
* 모니터링을 위해 중앙에서 커스텀 에러코드를 관리

<br>
<br>

## `AOP를 이용한 공통 관심사 분리`
## [📑 AOP 전역처리](https://jaymon.notion.site/Valid-AOP-6d8e721bc4c54bbe8d9631da66a332ee?pvs=4)
<img width="1087" alt="AOP1" src="https://github.com/JayMon0327/SpringStudy/assets/124110982/72cfd194-ceaa-4c66-9439-d49f4628a4a4">
<img width="1087" alt="AOP2" src="https://github.com/JayMon0327/SpringStudy/assets/124110982/4ef4a9b9-179c-43f9-8b3e-28b8ed096552">

<br>
<br>

## `ERD`
<img width="1318" alt="ERD" src="https://github.com/JayMon0327/SpringStudy/assets/124110982/690a6d29-90c7-43aa-88fd-befdfb56405d">

<br>
<br>

<hr>
<hr>

### 작업 Docs 문서

<details>
  <summary>📌작업 Docs 문서</summary>

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


</details>

