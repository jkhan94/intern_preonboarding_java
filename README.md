# 한 달 인턴 온보딩 과제 (백엔드 자바)
## 요구사항
**API 요구사항**
- 회원가입 
    - URL: /signup
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
        
- 로그인 
    - URL: /sign
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

**JUnit를 이용한 JWT Unit 테스트 코드 작성**
- 토큰 발행과 유효성 확인: Access / Refresh Token 발행과 검증에 관한 **테스트 시나리오** 작성 <br>
    https://preasim.github.io/39    
    [https://velog.io/@da_na/Spring-Security-JWT-Spring-Security-Controller-Unit-Test하기](https://velog.io/@da_na/Spring-Security-JWT-Spring-Security-Controller-Unit-Test%ED%95%98%EA%B8%B0)
<br><br>

**배포**
- AWS EC2 에 배포하기
<br><br>

**API 접근과 검증**
- Swagger UI 로 접속 가능하게 하기
<br>

___

<br>

## API 명세서
1. /api/auth/signup : 회원가입
2. /api/auth/sign : 로그인
3. /api/auth/refresh : 리프레시 토큰이 유효하면 액세스 토큰 재발급 (질문할 것이 있어 추가)

  
* swagger URL : /swagger-ui/index.html

<br>

___

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
