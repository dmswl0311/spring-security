# spring-security

src>main>resources>application-oauth.yml 파일 추가

```text
spring:
  security:
    oauth2.client:
      registration:
        google:
          clientId: xxxxxxxxxxxxxxx
          clientSecret: xxxxxxxxxxxxxxx
          scope:
            - email
            - profile
        kakao:
          clientId: xxxxxxxxxxxxxxx
          clientSecret: xxxxxxxxxxxxxxx
          clientAuthenticationMethod: post
          authorizationGrantType: authorization_code
          redirectUri: "{baseUrl}/{action}/oauth2/code/{registrationId}"
          scope:
            - profile_nickname
            - profile_image
            - account_email
          clientName: Kakao
      provider:
        kakao:
          authorizationUri: https://kauth.kakao.com/oauth/authorize
          tokenUri: https://kauth.kakao.com/oauth/token
          userInfoUri: https://kapi.kakao.com/v2/user/me
          userNameAttribute: id
```
