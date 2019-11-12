# ToffeeStory

## Member
- 남혁민
- 이세라
- 전유리

## 구조
- BackEnd
    - SpringBoot 사용
    - Front에 API 제공(RESTController로 구성) 
    - Server Port : 8098

- FrontEnd
    - axios 통신을 이용해서 BackEnd API를 호출하는 방식
    - Server Port : 8080
    
## 기술 스택
- BackEnd
    - SpringBoot
    - Java (JDK 12)
    - JPA
    - MYSQL 
    - SpringSecurity
    
- FrontEnd
    - Vue-Cli

- Common
    - AWS EC2
    - AWS RDS

## AWS 접속 방법
> AWS 접속하기 위해서는 AWS 계정에서 자신의 IP를 변경 후 접속해야 한다.
  
- Mac OS 실행 방법
    - toffeestory.pem 파일을 ssh관리 폴더(~/.ssh/)에 복사 붙여넣기한다.
    - config 파일에 Host, HostName, User, pem파일 등록
    - ssh 등록한 Host이름으로 실행 (예: ssh toffeestory)
  
- Windows OS 실행 방법 
    - putty 설치 (혹은 비슷한 프로그램 설치)
    - toffeestory.pem 파일을 다운 받은 후 PuttyGen 프로그램 실행 후 pem 파일을 .ppk 파일로 변환
    - Putty 실행 후 Auth에 변환한 ppk 파일 등록 후 user@public IP 주소로 실행한다.

## 프로젝트 실행 방법
- BackEnd
    - Intellij IDEA 사용
        - BackendApplication 실행
    - jar 파일 사용
        - gradle로 build (./buildew clean build)
        - backend/build/libs 폴더 경로로 이동
        - java -jar 생성된파일.jar 실행
        
- FrontEnd
    - FrontEnd 폴더에서 npm run serve 실행
