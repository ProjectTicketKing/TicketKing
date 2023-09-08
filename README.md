# TicketKing

티켓팅 연습 사이트, **TicketKing**

<img src="https://i.ibb.co/hRcMTcb/The-Heartbreakers.jpg" width="1000" height="400" >

## 서비스 소개
티켓팅은 경쟁이 매우 치열한 프로세스입니다.
티켓은 한정되어 있고 수요가 높기 때문에 신속하고 효과적인 티켓 구매 기술을 연습하는 것이 중요합니다.
    
저희 서비스는 사용자 입장에서 치열한 시뮬레이션을 경험할 수 있는 것에 초점을 두어
티켓팅에서 중요한 **1)서버 뚫기와 2)예매 과정을 경험해 보는 것**으로 아이디어를 기획하게 되었습니다.

지금부터, **티켓킹**과 함께 연습해보세요!

### ✨ What?
1. **전략 짜기!** 연습 사이트를 통해 실제 상황을 시뮬레이션하고 성공을 위해 전략을 연습해 볼 수 있습니다.
2. **실수 줄이기!** 시간이 생명이기에 실제와 유사한 환경에서 연습하며 실수를 줄일 수 있습니다.
3. **자신감 향상 !** 반복 연습을 통해 티켓 구매 과정에 대한 자신감을 키울 수 있습니다.
 
## 개발기간
- 2023년 06월 19일 ~ 2023년 07월 14일

## 팀원  
- 팀장 : 박채원
- 팀원 : 심예설, 김은지
<img width="625" alt="스크린샷 2023-08-11 오후 8 55 00" src="https://github.com/ProjectTicketKing/TicketKing/assets/82140052/dd6e1751-75aa-4368-9060-78a566310e0e">

## 🔧 Tools 
- JAVA 17
- Gradle
- Database - MySQLDB
- SpringBoot
- SpringDataJPA - QueryDSL
- SpringSecurity
- Nginx
- Docker
- Jenkins (CI/CD)
- NaverCloudPlatform
- Timeleaf
- Tailwind, DaisyUI

## 테이블 (다이어그램)
![image](https://github.com/ProjectTicketKing/TicketKing/assets/89733207/3283ec6a-fe99-43e6-830f-ab6b214a276e)

## 사이트
https://www.ticketking.co.kr/

##  💻  프로젝트 설명 (주요 기능)
회원가입/로그인
  1. 기본 회원가입 및 로그인
  2. 유효성 검증

실제 경쟁 환경 & 가상 경쟁 환경 선택 
  a. 실제 경쟁 환경
    - 실제 사용자들끼리 경쟁끼리의 경쟁 환경 구성
    - 실제 사용자들이 적용된 대기열 백엔드 구현
    - 실제 사용자들간의 좌석 선택 경쟁 기능 구현 
  b. 가상 경쟁 환경 
    - 난이도 선택에 따라 가상의 사용자들과 경쟁 가능하도록 환경 구성

서버 타임 기능
  1. 게임 시작 시간 선택 가능 (5초,30초,1분,3분,5분 후)

좌석 예매 기능
  1. 선택한 공연장에 따라 다른 좌석 UI 및 상태 제공
  2. socket을 이용하여 실시간 좌석 상태 확인

난이도 선택 기능
  1. 대기열 조절 
  2. 좌석 상태 변경 속도 조절
  3. 사용자 연습 기록 기능



    
