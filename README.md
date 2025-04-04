Work From Home (WFH) Request Application



Prerequisites

Before installation, ensure that your system meets the following requirements:

Java Development Kit (JDK)

Maven: Default for Spring Boot

PostgreSQL- Installed and running

IDE ( IntelliJ IDEA)

Postman – API Testing Tool

Project Setup

Step 1: Create a Spring Boot Project

Use Spring Initializr

Add the following dependencies:

Spring Web (for REST APIs)

Spring Boot Starter Data JPA (for database interactions)

PostgreSQL/MySQL (for database, choose as per your preference)

Spring Boot DevTools (for development convenience)

Configuring the Application

Update the application.properties file in the  src/main/resources directory with the correct database credentials and configurations.

Example (for PostgreSQL):

spring.application.name=WFH-app
spring.jpa.hibernate.ddl-auto=update
spring.jpa.hibernate.show-sql=true
spring.datasource.url=jdbc:postgresql://localhost:5432/genrichers
spring.datasource.username=postgres
spring.datasource.password=admin





Application Structure

Define WFH Request Entity

Create a Repository

Implement the Service Layer

Create Controller (REST API Endpoints)

Run the Application

Start PostgreSQL

Run Spring Boot Application



Test API Endpoints Using Postman

Submit WFH Request (POST Request)

URL: http://localhost:8080/api/employee/wfh_request/7

Body (JSON):

{

    "wfh_date":"2025-03-28", 

    "reason":"personal" 



}



Get Requests by Employee (GET Request)

URL: http://localhost:8080/api/employee/get_wfh_request/3

Get All Requests (GET Request for Admins)

URL: http://localhost:8080/api/admin/get_all_request/1

Get All Requests (GET Request for Manager)

URL: http://localhost:8080/api/manager/get_all_request/2

Get All Pending Requests (GET Request for Manager)

URL: http://localhost:8080/api/manager/get_all_status_request/2/pending

Approve Or Reject (POST Request For Manager)

URL: http://localhost:8080/api/manager/response/2/3

Body (JSON):

{



    "status":"approved" 





} 