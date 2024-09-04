# 한 달 인턴 온보딩 과제 (백엔드 자바)
## 요구사항
**Spring Security 기본 이해**
- [ ]  Filter란 무엇인가?(with Interceptor, AOP)
- [ ]  Spring Security란?
<br>

**JWT 기본 이해**
- [ ]  JWT란 무엇인가요?
<br>

**유닛 테스트 작성**
- [ ]  JUnit를 이용한 JWT Unit 테스트 코드 작성해보기    
    https://preasim.github.io/39    
    [https://velog.io/@da_na/Spring-Security-JWT-Spring-Security-Controller-Unit-Test하기](https://velog.io/@da_na/Spring-Security-JWT-Spring-Security-Controller-Unit-Test%ED%95%98%EA%B8%B0)
- [ ] 토큰 발행과 유효성 확인: Access / Refresh Token 발행과 검증에 관한 **테스트 시나리오** 작성하기
<br>


**API 요구사항**
- 회원가입 /signup
    - Request Message        
    
        ```json
        {
        	"username": "JIN HO",
        	"password": "12341234",
        	"nickname": "Mentos"
        }
        ```
        
    - Response Message
        
        ```json
        {
        	"username": "JIN HO",
        	"nickname": "Mentos",
        	"authorities": [
        			{
        					"authorityName": "ROLE_USER"
        			}
        	]		
        }
        ```
        
- 로그인 /sign
    - Request Message
        
        ```json
        {
        	"username": "JIN HO",
        	"password": "12341234"
        }
        ```
        
    - Response Message
        
        ```json
        {
        	"token": "eKDIkdfjoakIdkfjpekdkcjdkoIOdjOKJDFOlLDKFJKL"
        }
        ```
<br>

**배포해보기**
- [ ]  AWS EC2 에 배포하기
<br>

**API 접근과 검증**
- [ ]  Swagger UI 로 접속 가능하게 하기
<br>

**AI-assisted programming**
- [ ]  AI 에게 코드리뷰 받아보기
<br>

**Refactoring**
- [ ]  피드백 받아서 코드 개선하기
<br>

**마무리**
- [ ]  AWS EC2 재배포하기
<br>

**최종**
- [ ]  인텔리픽 과제/코테 코칭 신청하기
<br><br>

___

<br>

## API 명세서
http://localhost:8080/swagger-ui/index.html
http://54.180.136.107:8080/swagger-ui/index.html

<br>

## 커밋 컨벤션
* feat : 새로운 기능 추가 (초기 세팅)
* add : 새 파일 추가
* update : 기존 기능/파일 수정
* del : 기능/파일 삭제
* fix : 버그 수정
* style : 코드 포맷팅, 세미콜론 누락, 코드 변경이 없는 경우
* refactor : 코드 리펙토링
* test : 테스트 코드, 리펙토링 테스트 코드 추가
* setting : gradle, git 등 설정 파일 추가, 변경, 삭제
* docs : 문서 수정
