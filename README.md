# spring-security

src>main>resources>application-oauth.yml 파일 추가

```text
spring:
  security:
    oauth2.client:
      registration:
        google:
          clientId: xxxxxxxxx
          clientSecret: xxxxxxxxx
          scope:
            - email
            - profile
```
