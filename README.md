# QA_Automation_Servicenow
- QA_Automation 과 Servicenow 인스턴스를 연동해주는 중간 다리역할의 기능
  1. BLF(Broken Link Finder) 연동
  2. Filter 기능 추가
---
## 개발 환경
- JDK 17 이상
- Springboot 2.7.17
---
## 참고 사항
- localhost:28080
- Secret Key
  ```
  aGFsbHN0ZWVsZmx5aGFyZGx5Y2hpY2tlbmdpdmluZ3BhcnR5b3VycHJpemVzaG93bnQ=
  ```
- Servicenow 에서 생성된 JWT Token 의 Secret Key 는 Base64 로 인코딩 되지 않아, 현재 인코딩 되지 않은 값을 받고 있음.
  만약 Postman 등 api 테스트 시, 아래와 같이 Unchecked 설정 필요
  ![image](https://github.com/user-attachments/assets/8f6fd444-c8bf-45bc-b318-70cd5e124604)
