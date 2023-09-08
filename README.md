# TicketKing

티켓팅 연습 사이트, **TicketKing**  

<img width="795" alt="스크린샷 2023-09-08 오후 7 50 30" src="https://github.com/ProjectTicketKing/TicketKing/assets/82140052/81c50e3c-2e10-452e-9a6f-c876c5f51ff6">

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
- 1차 : 2023년 06월 19일 ~ 2023년 07월 14일
- 2차 : 2023년 08월 11일 ~ 2023년 09월 08일

## 팀원  
- 박채원, 심예설  
<img width="795" alt="스크린샷 2023-09-08 오후 6 33 32" src="https://github.com/ProjectTicketKing/TicketKing/assets/82140052/4c6def74-cf77-4781-9ceb-727afe91c7cc">


## 🔧 Tools 

<div align=center> 
  <img src="https://img.shields.io/badge/JAVA 17-407999?style=for-the-badge&logo=JAVA 17&logoColor=white">
  <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
  <img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white">
  <img src="https://img.shields.io/badge/Spring Security-6DB33F?style=for-the-badge&logo=Spring Security&logoColor=white"> 
  <img src="https://img.shields.io/badge/thymeleaf-005F0F?style=for-the-badge&logo=thymeleaf&logoColor=white">
  <img src="https://img.shields.io/badge/lombok-C02E18?style=for-the-badge&logo=lombok&logoColor=white">
  <br>
  
  <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
  <img src="https://img.shields.io/badge/SpringDataJPA-53B421?style=for-the-badge&logo=SpringDataJPA&logoColor=white">  
  <img src="https://img.shields.io/badge/QueryDSL-008ED0?style=for-the-badge&logo=QueryDSL&logoColor=white">  
  <img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">
  <br>
    
  <img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black">
  <img src="https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white">
  <img src="https://img.shields.io/badge/Tailwind-06B6D4?style=for-the-badge&logo=Tailwind&logoColor=white">
  <img src="https://img.shields.io/badge/DaisyUI-5A0EF8?style=for-the-badge&logo=DaisyUI&logoColor=white">
  <br>
  
  <img src="https://img.shields.io/badge/Nginx-009639?style=for-the-badge&logo=Nginx&logoColor=black">
  <img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=Docker&logoColor=black">
  <img src="https://img.shields.io/badge/NaverCloudPlatform-00CE89?style=for-the-badge&logo=NaverCloudPlatform&logoColor=black">
  <br>
  
</div>

## 테이블 (다이어그램)  
<img width="800" height="500" alt="스크린샷 2023-09-08 오후 7 41 59" src="https://github.com/ProjectTicketKing/TicketKing/assets/82140052/4de6690d-fa9b-471c-8943-5f1134a067b2">  

## API명세 
![image](https://github.com/ProjectTicketKing/TicketKing/assets/89733207/a5822a14-1f6f-4dab-b013-fe7e0ca76176)

## 사이트
https://www.ticketking.co.kr/

##  💻  프로젝트 설명 (주요 기능)
### 1. 회원가입/로그인  

![ezgif com-optimize (2)](https://github.com/ProjectTicketKing/TicketKing/assets/82140052/1e30a25e-860b-4ced-8189-577116ef94e0)

- [x] 기본 회원가입 및 로그인
- [x] 유효성 검증

### 2. 실제 경쟁 환경 & 가상 경쟁 환경 선택 

#### Ver1. 실제 경쟁 환경 : 실제 사용자들끼리 경쟁끼리의 경쟁 환경 구성  

   ![ezgif com-optimize (3)](https://github.com/ProjectTicketKing/TicketKing/assets/82140052/130207be-72df-4036-8635-b1fc88bacab9)  
   
  
- [x] 실제 사용자들이 적용된 대기열 백엔드 구현
- [x] 실제 사용자들간의 좌석 선택 경쟁 기능 구현 
    
#### Ver2. 가상 경쟁 환경 : 난이도 선택 기능을 추가하여 난이도에 따라 가상의 사용자들과 경쟁 가능하도록 환경 구성   

   ![ezgif com-optimize (1)](https://github.com/ProjectTicketKing/TicketKing/assets/82140052/3db82e99-9ba5-49d7-bdfd-3f88a91e1e0d)  

     
- [x] 클릭 속도에 따라 다른 대기열 구현
- [x] 게임 시작 시간 선택 가능 (5초,30초,1분,3분,5분)  
- [x] 좌석 상태 변경 속도 조절

 ### 3. 좌석 예매 핵심 기능    
- 선택한 공연장에 따라 다른 좌석 UI 및 상태 제공
     
     <img width="478" alt="스크린샷 2023-09-08 오후 4 38 08" src="https://github.com/ProjectTicketKing/TicketKing/assets/82140052/15a03df8-c13f-470d-9e06-46bee99ce30e">      
     <img width="478" alt="스크린샷 2023-09-08 오후 4 39 40" src="https://github.com/ProjectTicketKing/TicketKing/assets/82140052/727f67dd-4155-4104-bfae-ab30c3c4916d">  
 
- socket을 이용하여 실시간 좌석 상태 확인  
     
     ![ezgif com-optimize (4)](https://github.com/ProjectTicketKing/TicketKing/assets/82140052/729c3ec8-d8ff-4768-a32d-d72ea463cb0c)



   
