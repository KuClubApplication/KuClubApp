- 업데이트 해주신 코드 틈틈이 pull 하면서 작업하려고 하니, 에러나는 상태에서 push 하지 말아주세요!
---
### 주요 구조
- 연결 구조: User(data class) - UserDao - UserRepository - NavUserViewModel - MainScreen(LoginScreen, RegisterScreen, ClubListScreen, SettingScreen, CategoryScreen, MypageScreen이 속함)
- 스크린 추가 시 코드 추가해야 하는 파일: NavRoutes 클래스, MainScreen.kt의 NavHost 부분, Topbar.kt의 'Route -> {특정 문자열}' 부분, MainActivity에 있는 MainScreen의 인자 (컨플릭트 어떻게 하지..)
  - Screen의 인자로 줘야 하는 것: navHostController, navUserViewModel, 특정 스크린의 ViewModel (ex. navNoticeViewModel)
---  
### 기타 코드 설명
- MainActivity : 자동로그인 여부 및 JWT 유무에 따라 진입점 다르게 설정.
- UserDao : Realtime database 액세스 하도록 수정함.
- retrofitclass.kt & apiinterface.kt : express 서버와 통신하기 위한 네트워크 연결 및 API 인터페이스.
- datastore.kt : 자동로그인 체크 여부와 JWT를 로컬 Datastore에 저장 
- GMailSender : SMTP 프로토콜로 인증번호 메일 전송
- AndroidManifest.xml : android:usesCleartextTraffic="true" 이나 퍼미션 중요
- res > drawable : 사진 및 아이콘
---
### 서버 정보
- kuclubServer.js : express 프레임워크, firebase-auth 사용하여 토큰(JWT) 생성 및 검증, 주로 POST 요청 처리.
- 토큰 생성 및 검증 서버
  - AWS EC2에서 pm2로 실행
  - 서버 URL 및 엔드포인트
    - http://3.34.14.100:5000/login
    - http://3.34.14.100:5000/verifyToken
    - http://3.34.14.100:5000/sendIdToken
