 # InjoyTrip   
![injoytrip](https://github.com/keunmoj/InjoyTrip/assets/94428183/587da266-7a7e-488a-a86d-2c9c92ac6d05)
## 서비스 내용
전국 관광지 공공데이터를 활용한 관광지 검색, 사용자별 여행 일정 만들기를 제공하는 웹 사이트
## 개발 기간
2023-05-18 ~ 2023-05-25
## 기술 스택
* 백엔드
  * Java
  * Spring Boot
  * MyBatis
  * MySQL
* 프론트엔드
  * HTML
  * CSS
  * Java Script
  * Vue.js
 
## 주요 기능
![image](https://github.com/keunmoj/InjoyTrip/assets/94428183/da36549d-79ac-46b8-9d13-27e48bf0941c)
* 관광지 검색
  * 왼쪽 사이드바에 검색된 관광지 목록 출력한다.
* 위시리스트
  * 사용자가 원하는 관광지를 장바구니처럼 추가, 삭제 할 수 있다.
![image](https://github.com/keunmoj/InjoyTrip/assets/94428183/2c2d33e7-e3e0-4882-8a2f-415f00a1a600)
* 여행 계획 생성
  * 사용자가 원하는 관광지를 모아 하나의 여행 계획 플랜을 생성할 수 있다.
* 여행 계획 조회 및 변경
  * 개인 일정을 확인할 수 있으며 주요 키워드를 바탕으로 여행 정보를 한 눈에 알아볼 수 있다.
  * 여행 일정에 변동사항이 생길 경우 변경이 가능하다.

## 부가 기능
![image](https://github.com/keunmoj/InjoyTrip/assets/94428183/8801ae62-d2e4-4094-bcd9-9bdb5a640699)
* 게시판
  * 여행지 정보 교환 커뮤니티

## 프로젝트 진행하면서 어려웠던 점과 극복
* 개발 일정 문서 관리
  * 어려웠던 점: 개발 중 여러가지 대외 변수로 인해 계획이 지켜지지 않았던 경우가 있었다.
  * 해결: 개발 기한을 엄수하기 위하여 구현해야 할 기능에 우선순위를 부여하여 개발을 진행했다. 다행히 필수 기능 구현이 빠르게 되어 어긋난 일정을 정상화 할 수 있었다.
* DB 테이블 설계
  * 어려웠던 점: 기획 단계에서 결정한 "사용자 여행 플랜 카드 생성" 기능을 위한 user(사용자) 테이블과 attraction_info(여행지 정보)테이블과 card테이블 간의 관계정의, 설정 문제
  * 해결: 사용자는 여러개의 카드를 가질 수 있으며 하나의 카드에는 여러개의 여행지 정보가 들어갈 수 있다. 여기서 card 테이블에 여러개의 여행지 정보를 넣는 방법으로 card_id와 content_id(여행지 정보 id)를 칼럼으로 가지는 card_contents 테이블을 생성하여 관계 정의를 해결했다.
