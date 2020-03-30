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


* * *
### API
Method	| Path	| Description	| User authenticated	
------------- | ------------------------- | ------------- |:-------------:|
POST	| /bank/housingfinancial/statistics/save	|데이터 파일에서 각 레코드를 데이터베이스에 저장하는 API 	| o
GET	| /bank/housingfinancial/list	| 주택금융 공급 금융기관(은행) 목록을 출력하는 API	| o
GET	| /bank/housingfinancial/year/maxamount	| 각 년도별 금융기관의 지원금액 합계를 출력하는 API | o
GET | /bank/housingfinancial/year/exchangebank/avg/minmaxamount	| 전체 년도에서 외환은행의 지원금액 평균 중에서 가장 작은 금액과 큰 금액을 출력하는 API 개발 	| o


#### 공통 에러
>성공 응답(httpstatus code 200)을 제외한 에러 응답의 경우 status, message 필드로 구성된다.
>아래 메시지 구조는 아래와 같다.
>에러메시지 정의는 locale/messages_ko.properties 존재한다.
``` 
{
    "error": {
        "status": 405,
        "message": "지원하지 않는 형식의 메서드입니다."
    },
    "_links": {
        "self": {
            "href": "호출한 url 주소"
        }
    }
}
```   

> 요청에 대한 성공시에는 `HttpStatus code 200`이며,
> 데이터가 없을 경우 `HttpStatus code 204` 이다.



* * *
