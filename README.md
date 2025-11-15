# [spring-security-demo](https://github.com/sombriks/spring-security-demo)

some configuration samples for spring security

## requirements

- java 25

## initial setup

project generated via [spring initializr](https://start.spring.io/#!type=maven-project&language=java&platformVersion=4.0.0-SNAPSHOT&packaging=jar&configurationFileFormat=yaml&jvmVersion=25&groupId=com.example&artifactId=demo&name=demo&description=Demo%20project%20for%20Spring%20Boot&packageName=com.example.demo&dependencies=security,h2,liquibase,web,data-jpa)

## running

```bash
./mvnw clean compile spring-boot:run
```

Then pont the browser to one of these urls:

- <http://localhost:8080/>
- <http://localhost:8080/protected>
- <http://localhost:8080/admin>

## testing

```bash
./mvnw clean compile test
```

## Noteworthy

Main branch is the *zero-config* setup. See other branches to better understand
how to secure a spring boot service.

