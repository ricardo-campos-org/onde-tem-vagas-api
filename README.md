# Onde Tem Vagas

This is a service that can help you find jobs, based on terms of search, it'll bring to you everything that matches.

# Developer corner

## Requirements

- Java OpenJDK 11 (I recommend [sdkman](https://sdkman.io/install))
- Maven (You can use sdkman)
- Docker

## Suggestions

- Postman
- DBeaver

## Running

- You can get the service running with `./mvnw spring-boot:run`
- The server should start at the port 8080

## Running checks and testing

- For checkstyle: `./mvnw --no-transfer-progress checkstyle:checkstyle -Dcheckstyle.skip=false --file pom.xml`
- For unit tests: `./mvnw --no-transfer-progress test --file pom.xml`
- For integration tests: `./mvnw --no-transfer-progress verify -P integration-test --file pom.xml`
- And code coverage: `./mvnw --no-transfer-progress clean verify -P test-everything --file pom.xml`