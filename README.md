# Simple Delivery System

## Project Description
This service created for simple delivery system. It has company register and basic auth for e-commerce companies. 
It also provides for creating new delivery with dynamic search API for registered e-commerce companies.

Postman collections is in the main directory. (name = DeliverySystem.postman_collection.json)

#### Service has three database entity for these operations:
COMPANY TABLE:
- COMPANY_ID
- COMPANY_NAME
- COMPANY_ADDRESS
- COMPANY_WAREHOUSE_ADDRESS

DELIVERY TABLE:
- DELIVERY_ID
- SENDER_COMPANY (OneToOne relation with COMPANY Table)
- ORDER_ID
- SENDER_WAREHOUSE_ADDRESS
- DELIVERY_ADDRESS
- REQUESTER_INFORMATION
- STATUS
- CREATION_DATE
- UPDATE_DATE

COMPANY_USER TABLE:
- COMPANY_USER_ID
- USER_NAME
- PASSWORD
- COMPANY_ID (Related Company Table)
- ENABLED
- CREATION_DATE

#### System Rest API Definitions:
POST - /company/register:
  - It allows a new e-commerce company to register on the system. 
  - Request has the company's name, address, and warehouse address. 
  - In order to use other services in the system, a username and password specific to this company are created and returned in response.

POST - /v1/delivery
  - Create new delivery on the system.
  - It knows the relevant company that created the delivery from the login parameters.
  - Basic authentication with username and password is mandatory.

GET  - /v1/delivery
  - Queries on delivery information of logged in company in the system.
  - It works dynamic with jpa specifications. It means you can add more filters on request parameters like {URI}/v1/delivery?orderId=123&from=DATE&to=DATE
  - Basic authentication with username and password is mandatory.

## How to Install and Run the Project
### Requirements
JDK 8

Maven 3

Docker

### Installation
You should run this command on project home directory:

``` docker build -t springio/gs-spring-boot-docker . ```

This command builds an image and tags it as 'springio/gs-spring-boot-docker'

#### Alternative Installation
You can run this command on project home directory:

``` mvn clean install ```

### Run Project
You should run this command:

``` docker run -p 8080:8080 springio/gs-spring-boot-docker ```

#### Alternative Run Project
You can run this command on project home directory:

``` java -jar target/DeliverySystem-0.0.1-SNAPSHOT.jar ```

### Test
You can test with this curl command:

``` curl -L -X POST 'http://localhost:8080/company/register' -H 'Content-Type: application/json' -H 'Cookie: JSESSIONID=2EFB210B511D36BC676EB94B194299B8' --data-raw '{ "companyName": "ABC Mobilya", "companyAddress": "Cumhuriyet Mahallesi Dalaman Muğla Türkiye", "companyWarehouseAddress": "Karaçulha Mahallesi Fethiye Muğla Türkiye" }' ```

expected response is:

``` { "userName": "abcmobilya", "password": "df0eaa63-9175-4259-abb8-922c604492e7" } ```

##### After the project is up, you can control H2 DB Console on this link -> http://localhost:8080/h2-console/ You can need to update JDBC URL = jdbc:h2:mem:deliverysystem After this update you can push connect button.