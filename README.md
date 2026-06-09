# MB Account Service

A Spring Boot–based microservice for managing customer accounts.  
This service provides APIs for creating accounts, retrieving account details, and generating account numbers with Luhn check digit validation.

## Features
- Create new customer accounts with validation (`AccountDto`)
- Retrieve accounts by customer number
- Generate unique account numbers using branch code, year, and sequence
- Map between DTOs and entities (`AccountMapper`)
- Persist accounts with JPA (`AccountRepository`)
- Return structured responses (`AccountResponse`, `SavingDto`)

## Tech Stack
- Java 17+
- Spring Boot
- Spring Data JPA (H2/PostgreSQL/MySQL compatible)
- Maven
- JUnit 5 + Mockito for testing

## Getting Started

## Prerequisites
- Install [Java 17](https://adoptium.net/)
- Install [Maven](https://maven.apache.org/)
- Clone the repository:
  ```bash
  git clone https://github.com/pjadomingo/mb-account-service.git
  cd mb-account-service
- Run the app
  mvn clean install spring-boot:run
- Access the app
  http://localhost:8081/api/v1/account
  POST: create account
  GET(/{customerNumber}): retrieve account by customer number 
