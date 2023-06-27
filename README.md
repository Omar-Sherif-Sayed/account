# Nagarro Account Project

### Description

This project represents a sample use of spring security by adding InMemory 2 users Amin and User
with 3 APIs Login, Logout and search.

### Languages and Tools:


<p align="center">

![Java](https://img.shields.io/badge/java-%234e7896.svg?style=for-the-badge&labelColor=#f58219&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/spring_boot-%236DB33F.svg?style=for-the-badge&logo=springboot&logoColor=white&link=https://spring.io/)
![Spring Security](https://img.shields.io/badge/spring_security-%236DB33F.svg?style=for-the-badge&logo=springsecurity&logoColor=white)
![Microsoft Access](https://img.shields.io/badge/microsoft_access-%23A4373A.svg?style=for-the-badge&logo=microsoftaccess&logoColor=white)
![Swagger](https://img.shields.io/badge/swagger-%2385EA2D.svg?style=for-the-badge&logo=swagger&logoColor=black)
![sonarlint](https://img.shields.io/badge/sonarlint-%23CB2029.svg?style=for-the-badge&logo=sonarlint&logoColor=white)
![Sonar Qube](https://img.shields.io/badge/sonar_qube-%234E9BCD.svg?style=for-the-badge&logo=sonarqube&logoColor=white)
![Redis](https://img.shields.io/badge/redis-%23DC382D.svg?style=for-the-badge&logo=redis&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%232496ED.svg?style=for-the-badge&logo=docker&logoColor=white)
![GitHub](https://img.shields.io/badge/github-%23181717.svg?style=for-the-badge&logo=github&logoColor=white)
![Intellij IDEA](https://img.shields.io/badge/intellij_idea-%23000000.svg?style=for-the-badge&logo=intellijidea&logoColor=white)

</p>

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

- Install Java 17
- Install Intellij
- Install Docker
- Install Gradle
- Make encoding in Intellij settings UTF-8, so you can see Arabic messages.
- Clean & Build Project, via command line in the classpath `./gradlew clean build`
- Run the project via IntelliJ via running the main method in AccountApplication
- Test the APIs You can find a postman collection in ``` classpath:src/main/resources/collections ```
- Run `./gradlew jacocoTestReport` for test coverage by Jacoco and see the result
  at ``` classpath:build/reports/jacoco/test ```
- Run `./gradlew jacocoTestCoverageVerification` for test coverage validation by Jacoco and see the result
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
  inside the same web page in sonarqube I generated a new project and extract ``` sonar.login ``` and put it in the
  file ``` build.gradle ```
- Connection with Ms Access is hard to work, I think in first that it can work normally in Spring Data JPA, and it
  can run in an easy way. <br>
  after using JdbcTemplate the query get errors like: <br>
  ``` SQL state [S0002]; error code [208]; Invalid object name [tableName] ``` <br>
  after some searching and retrying different ways, it was a problem in database connection it without throwing <br>
  a clear error, to fix it I just created a DatabaseConfig file for it, and it gives a lot of errors when lunching <br>
  application but the code functionality didn't effect, this stile an issue I will try to fix it.
- Swagger not working yet with Spring 3.1.1, so I just Used OpenAPI and it's swagger
- Redis repository retrieving the values by id only, that is not as expected, <br>
  I can't retrieve data by username (id) and token

### In Progress

- Doing a Unit Test
- Update The READE.md file

### Notes

- After doing things in (in Progress) section will be updated and deleted.

### Useful URLs After running the project locally

[OpenAPI](http://localhost:8070/api/account/swagger-ui/index.html) <br>
[SonarQube](http://localhost:9000/)

