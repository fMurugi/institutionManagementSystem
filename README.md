# Project Name
Institution Management System

## Prerequisites
Make sure you have the following installed on your system:
- Java Development Kit (JDK) 11 or later
- Maven
- MySQL

## Configuration
Before running the project, you need to configure the database properties. Follow these steps:

1. Open the `application.yaml` file located in `src/main/resources`.
2. Modify the following properties according to your database setup:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/mydatabase
    username: username
    password: password

## RUN
```
mvn clean package -DskipTests
java -jar target/instustionsManagement-0.0.1-SNAPSHOT.jar 



