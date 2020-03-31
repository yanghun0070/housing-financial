# housing-financial

- 이 프로젝트는 Spring boot를 사용하여, 국내 주택금융 신용보증 기관으로부터 년도별 각 금융기관(은행)에서 신용보증한 금액에 대한 데이터를 통해, 기능명세에 대한 API 

- 회원제로 운영
 
## 서비스
- 회원가입 / 로그인 API
- 주택금융 공급 금융기관(은행)목록 API
- 년도별 각 금융기관의 지원금액 합계 API
- 각 년도별 각기관의 전체 지원금액 중에서 가장 큰 금액의 기관명을 출력하는 API
- 전체 년도(2005~2016)에서 외환은행의 지원금액 평균 중에서 가장 작은 금액과 큰 금액을 출력하는 API


### 구축 환경
- Java 11 
- Spring Boot 2.2.5.RELEASE
- Spring Security(로그인)
- gradle 6.2.2
- H2 DB  

### 실행 방법 

> ./gradlew compileQuerydsl build


* * *
### API
Method	| Path	| Description	| User authenticated	
------------- | ------------------------- | ------------- |:-------------:|
POST	| /bank/housingfinancial/statistics/save	|데이터 파일에서 각 레코드를 데이터베이스에 저장하는 API 	| o
GET	| /bank/housingfinancial/list	| 주택금융 공급 금융기관(은행) 목록을 출력하는 API	| o
GET	| /bank/housingfinancial/year/maxamount	| 각 년도별 금융기관의 지원금액 합계를 출력하는 API | o
GET | /bank/housingfinancial/year/exchangebank/avg/minmaxamount	| 전체 년도에서 외환은행의 지원금액 평균 중에서 가장 작은 금액과 큰 금액을 출력하는 API 개발 	| o
POST | /auth/signup	| 회원 가입(계정 생성) API 	| × 
POST	| /account/signin | ID, PW 로 로그인을 요청하면 토큰 발급   | × 


#### 공통 에러
>성공 응답(httpstatus code 200)을 제외한 에러 응답의 경우 status, message 필드로 구성된다.
>아래 메시지 구조는 아래와 같다.
>에러메시지 정의는 locale/messages_ko.properties 존재한다.
``` 
{
    "error": {
        "status": [상태코드],
        "message": [메시지 내용]
    },
    "_links": {
        "self": {
            "href": "호출한 url 주소"
        }
    }
}
```   
HttpStatus Code	| Message 
------------- | ------------------------- 
200 | 요청을 정상적으로 수행하였습니다. 
204 | 요청한 API의 결과를 찾을 수 없습니다. 
400 | 필수 파라미터가 누락되었거나 포맷이 일치하지 않습니다.  
403 | 요청한 API의 권한이 없습니다.
404 | 유효하지 않는 API 주소입니다.
405 | 지원하지 않는 형식의 메서드입니다.
500 | API 서비스 내부 시스템 오류가 발생하였습니다 관리자에게 문의하세요.
401 | 인증 실패하였습니다.




* * *

#### 계정생성

>회원 가입시 설정한 아이디/패스워드를 통해 계정생성을 진행한 후에, 로그인을 수행한다.  

###### Path
- /auth/signin

###### body element
- userId, password로 구성된다. 

``` 
curl -X POST -v -H "Accept: application/json" -H "Content-Type: application/json" -d '{  "userId": "user", "password" : "password" }' http://localhost:8080/auth/signup
```

### 로그인
>로그인 결과 인증 token과 username 을 응답 받는다.  
>인증 token는 JWT형태의 Jwt token 이다.  
>발급된 jwt token의 expire time은 24시간 이다.   
24시간 이후에는 token이 만료되어 사용할 수 없다.

###### Path
- /auth/signup

###### body element
- userId, password로 구성된다. 

``` 
curl -X POST -v -H "Accept: application/json" -H "Content-Type: application/json" -d '{  "userId": "user", "password" : "password" }' http://localhost:8080/auth/signup
```

>응답메시지 구조는 아래와 같다

``` 
{
    "result": {
        "username": "user",
        "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwicm9sZXMiOltdLCJpYXQiOjE1ODU2MjQ1MDUsImV4cCI6MTU4NTYyODEwNX0.3uFiGD4ECWcKhU1Gmdav7V8-3CZFqtWzMqr-2RxL0SM"
    },
    "_links": {
        "self": {
            "href": "http://localhost:8080/auth/signup"
        }
    }
}
``` 

####  API 조회

>__로그인시 발급 받은 jwt token을 Authorization Bearer {token값} 형태로 전송 해야한다.__  
> 주택금융 공급 금융기간 API 는 데이터 파일에서 각 레코드를 데이터베이스에 저장하는 API 를 우선적으로 호출되어야 한다.
> API 조회 시에 아래와 같이 로그인 시 받은 Token 값을 이용하여 로그인해야 한다.

###### Sample API Example 
``` 
> curl -X POST -H "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwicm9sZXMiOltdLCJpYXQiOjE1ODU2MjQ1MDUsImV4cCI6MTU4NTYyODEwNX0.3uFiGD4ECWcKhU1Gmdav7V8-3CZFqtWzMqr-2RxL0SM  -H "Accept: application/json" -H "Content-Type: application/json" 
http://[IP Address]:[Port]/[Path]
``` 
