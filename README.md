- 업데이트 해주신 코드 틈틈이 pull 하면서 작업하려고 하니, 에러나는 상태에서 push 하지 말아주세요~ 

- 연결 구조: User(data class) - UserDao - UserRepository - NavUserViewModel - MainScreen
- 스크린 추가 시 코드 추가해야 하는 파일: NavRoutes, MainScreen의 NavHost 부분
  
- MainActivity : 자동로그인 여부 및 JWT 유무에 따라 진입점 다르게 설정.
- UserDao : Realtime database 액세스 하도록 수정함. / 혹시 몰라서 Datastore 액세스 코드 주석 처리 해놓음. Firestore 안 쓰는 거 확정이면 지울 예정.
- retrofitclass.kt & apiinterface.kt : express 서버와 통신하기 위한 네트워크 연결 및 API 인터페이스.
- datastore.kt : 자동로그인 체크 여부와 JWT를 로컬 Datastore에 저장 
- GMailSender : SMTP 프로토콜로 인증번호 메일 전송
- kuclubServer.js : express 프레임워크, firebase-auth 사용하여 토큰(JWT) 생성 및 검증, 주로 POST 요청 처리. 

- 토큰 생성 및 검증 서버 (AWS EC2에서 실행)
  - 서버 URL: http://3.34.14.100:5000
  - 서버 엔드포인트 : /login, /verify Token, 
