# KreamCloneBackend

:raising_hand: Kream 소개

Kream은 한정판 신발 거래 사이트입니다. 모든 거래 체결 내역과 입찰가를 공개하여 구매자는 구매할 상품(구매입찰/ 즉시 구매)을 선택하며
판매자는 판매할 상품(판매입찰 / 즉시판매)을 등록하여 구매자와 판매차가 원하는 가격이 일치하면 거래가 체결되는 시스템입니다.

:mag: 프로젝트 주소 

:tv: 데모영상

---

프로젝트 기간 팀원 소개

:calendar: 기간 : 2021 10월 18일 ~ 2021년 10월 22일
 
👨🏻‍🤝‍👨🏻 Front: 권영준 & 김세연 & 심선아

👨🏻‍🤝‍👨🏻 Back: 강준규 & 구산하 & 김태웅

---

:dart: 개발목표

1. 서로 다른 개발환경에서의 연동(CORS) 
2. 회원가입 & Spring에서 JWT 방식의 로그인 
3. 신발 북마크(찜하기) 기능
4. 상세페이지 단일 사이즈 / 모든 사이즈 가격 조회
5. 거래페이지에서 거래 성공 , 실패여부 조회
6. 마이페이지에서 북마크 , 판매내역 , 구매내역 조회 

---

:computer: 사용기술

 | Front        | Back                         |
 | ------------ | ---------------------------- |
 | React, Redux | Spring boot, Spring Security |
 | Axios, Immer | MySQL , AWS RDS, JWT         |
 | S3           | AWS                          |

 
 ---

✔ 와이어 프레임

https://www.notion.so/Starting-Assignment-18ddfbed21ba4d9ab8a4aec28bcaa360

---

✔ API 설계

![API1](https://user-images.githubusercontent.com/78454649/138403070-98ded899-76d2-4e9f-b300-1d4604e73996.PNG)
![API2](https://user-images.githubusercontent.com/78454649/138403079-faf9dc4a-b6b2-4afb-91f5-8c594cdf37fc.PNG)
![API3](https://user-images.githubusercontent.com/78454649/138403087-68981181-1df4-4d77-9cfe-5c3ac5072620.PNG)

---

✔ 데이터베이스 설계

![테이블설계 수정](https://user-images.githubusercontent.com/78454649/138409205-999b70bd-2f5d-4c9d-82f6-ab95fae0fff9.png)

---

✔ DB TABLE JPA 

![DB JPA](https://user-images.githubusercontent.com/78454649/138405691-60087260-25ce-452d-92e4-8122e5853253.PNG)
![DB JPA2](https://user-images.githubusercontent.com/78454649/138405693-57050e2a-f01e-4db4-a0d6-60877d2a4c7e.PNG)


---
✔ 도메인 설계 & 로직

1. User Table과 Shoes Table과의 연관관계를 설정하기 위해 1:M - 중간 엔티티 - M:1 연관관계로 구현했습니다.
유저의 관심 상품 조회(bookmark), 상품의 받은 관심 수를 표현하기 위해 사용합니다.
2. User Table은 Order Table과 1:M 연관관계를 가집니다
유저가 현재 입찰을 통해 참여한 거래의 목록을 mypage에서 확인 가능
3-3. Order Table은 Shoes Table과 M:1 연관관계를 가집니다
해당 신발에 현재 생성되어있는 구매, 판매 입찰을 조회 하기 위해 사용
4. Order Table은 한가지의 Enum Column(TRADING ROLE, TRADETYPE)을 가지고 두개(BUY, SELL)의 거래 종류로 구분됩니다
그리고 Order Table의 Create, Delete는 두가지(Bidding, Matching)요청에 의해 발생합니다
5. Bidding(입찰) 요청인 경우 Table에 Buy또는 Sell 거래를 생성합니다. 해당 거래의 입찰요청은 구매요청인 경우 즉시구매가보다 적은 금액일 수 없고, 판매 요청인 경우 즉시 판매가보다 클 수 없습니다.
6. Matching(즉시거래) 요청의 경우 이미 클라이언트에 제공된 즉시 구매가/판매가(테이블의 최저, 최고가)를 통해서만 요청이 가능합니다.
7. 클라이언트로부터 Matching요청을 전달받은 경우 테이블에서 해당 가격의 거래를 찾고, 생성순서로 정렬하여 가장 오래된 거래를 삭제시킵니다.
8. Matching으로 제거된 거래건은 EndupOrder Table에 저장되어 최근 거래가격으로 활용됩니다.

