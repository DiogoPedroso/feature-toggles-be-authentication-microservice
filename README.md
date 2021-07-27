# Authentication Microservice

This is the Authentication and Authorization Microservice that was created for the "Feature Toggle" assignment. The users have been hardcoded into Spring's "InMemoryUserDetailsManager", userAdmin has access to the FE and userApp has access to the "get client features access list". Both users have "password" has the "password". Update the aplication yaml file according to your environment's config.

Requires JAVA 16 and Maven.

## Run Project

To run the project use the `./mvnw spring-boot:run` command on the root of the folder

## Build
To build the project use the `./mvnw compile` command on the root of the folder

## Test
To run the tests use the `./mvnw test` command on the root of the folder
