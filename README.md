# [spring-security-demo](https://github.com/sombriks/spring-security-demo)

Some configuration samples for spring security

## Requirements

- Java 25

## Initial setup

Project generated via [spring initializr](https://start.spring.io/#!type=maven-project&language=java&platformVersion=4.0.0-SNAPSHOT&packaging=jar&configurationFileFormat=yaml&jvmVersion=25&groupId=com.example&artifactId=demo&name=demo&description=Demo%20project%20for%20Spring%20Boot&packageName=com.example.demo&dependencies=security,h2,liquibase,web,data-jpa)

## Running

```bash
./mvnw clean compile spring-boot:run
```

Then pont the browser to one of these urls:

- <http://localhost:8080/>
- <http://localhost:8080/protected>
- <http://localhost:8080/admin>

## Testing

```bash
./mvnw clean compile test
```

Tests will use spring security test and checks endpoints for the proper
authentication outcome.

## Noteworthy

- The main branch is the *zero-config* setup. See other branches to better
  understand how to secure a spring boot service.
- [Basic authentication]
- [Form login]
- [JWT (sessionless) authentication]
