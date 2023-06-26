# Nagarro Account Project

### Description

This project represent sample use of spring security by adding InMemory 2 users Amin and User
with 3 APIs Login, Logout and search.

### Languages and Tools:

<p align="center"> <a href="https://www.docker.com/" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/docker/docker-original-wordmark.svg" alt="docker" width="40" height="40"/> </a> <a href="https://www.java.com" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-original.svg" alt="java" width="40" height="40"/> </a> <a href="https://redis.io" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/redis/redis-original-wordmark.svg" alt="redis" width="40" height="40"/> </a> <a href="https://spring.io/" target="_blank" rel="noreferrer"> <img src="https://www.vectorlogo.zone/logos/springio/springio-icon.svg" alt="spring" width="40" height="40"/> </a> </p>

- Spring Boot 3.1.1
- Spring Security
- Spring Data JDBC
- Spring Dat Redis
- Java 17
- Functional Programming
- Docker
- Sonarqube
- jacoco
- gradle
- redis
- logging
- localization
- OpenAPI
- Ms Access

### How to run the project

- Install Java 17 link[]
- Install Intellij
- Install Docker
- Install Gradle
- Make encoding in Intellij settings UTF-8, so you can see Arabic messages.
- Clean & Build Project, via command line in the classpath `./gradlew clean build`
- Run the project via Intellij via running the main method in AccountApplication
- Test the APIs You can find a postman collection in ``` classpath:src/main/resources/collections ```
- Run `./gradlew jacocoTestReport` for test coverage by Jacoco and see result
  at ``` classpath:build/reports/jacoco/test ```
- Run `./gradlew jacocoTestCoverageVerification` for test coverage validation by Jacoco and see result
  at ``` classpath:build/reports/jacoco/test ```
- Last point you can enable/disable it throw ``` build.gradle ``` file
  in ``` jacocoTestCoverageVerification.violationRules.rule.enabled ``` property by making it true to enable it or false
  to disable it.
- Run `./gradlew sonarqube` in the classpath to run the sonarqube

### APIs

- ``` /auth/login ```: for login the users and returning to them the Jwt Token
- ``` /auth/logout ```: for logout the users
- ``` /account/search ```: for getting statements last 3 months or search on it with date rang or amount rang or account
  id.

### Issues I faced

- It is not easy to run the project with sonarqube, first time it success then it is not. <br>
  you have to change the password. the default username: ``` admin ``` and password: ``` admin ```. <br>
  in my case I changed it throw the [SonarQube](http://localhost:9000/) locally by making new
  password: ``` sonar ``` <br>
  inside the same web page in sonarqube I generated new project and extract ``` sonar.login ``` and put it in the
  file ``` build.gradle ```
- Connection with Ms Access is hard to work, I Think in the first that it can work normally in Spring Data JPA, and it
  can run in easy way. <br>
  after using JdbcTemplate the query get errors like: <br>
  ``` SQL state [S0002]; error code [208]; Invalid object name [tableName] ``` <br>
  after some searching and retrying different ways, it was a problem in database connection it without throwing <br>
  a clear error, to fix it I just created a DatabaseConfig file for it, and it gives a lot of errors when lunching <br>
  application but the code functionality didn't effected, this stile an issue I will try to fix it.
- Swagger not working yet with Spring 3.1.1, so I just Used OpenAPI and it's swagger
- New Security in Spring Boot 3.1.1 it is different that old version like 2.x.x, <br>
  we have ```  @PreAuthorize ``` annotation does not work as expected, it may be spring security implemented by wrong
  way (something missing) <br>
  or it is just because it is new version may be not working on it, but it is stable version, so I think It's first
  option.
- Redis repository retriveing the values by id only, that is not as expected, <br>
  I can't retrieve data by username (id) and token

### In Progress

- Doing a Unit Test
- Fixing ```  @PreAuthorize ``` Issue
- Update The READE.md file

### Notes

- After doing things in (in Progress) section will be updated and deleted.

### Useful URLs After running the project locally

[OpenAPI URL](http://localhost:8070/api/account/swagger-ui/index.html) <br>
[SonarQube](http://localhost:9000/)

