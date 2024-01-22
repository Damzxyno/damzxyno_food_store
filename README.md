# 6G7V0039 – Advanced Object-Oriented Programming

## Manchester Metropolitan University
Department of Science and Mathematics 

## Student Information
| Name          | Damilola Israel Oluwole |
|---------------|-------------------------|
| Student Id    | 23692640                |
| Student Email | 23692640 @gmail.com     |

## Assignment Information
| Assessment Set By | Conor Muldoon            |
|-------------------|--------------------------|
| Assessment Title  | The Wholesale Food Store |

## Table of Contents
- [Student Information](#student-information)
- [Assignment Information](#assignment-information)
- [Table of contents](#table-of-contents)
- [Overview](#overview)
- [Project Meta-Info](#project-meta-info)
- [Instructions](#instructions)
- [Assignment Template Form / Implementation Checklist](#Assignment-Template-Form-/-Implementation-Checklist)
- [Files Included](#files-included)

## Overview
The objective of this assignment is to develop a web based wholesale stock control and ordering system particularly for food products

## Project Meta-Info
| Java Version          | 17                      |
|-----------------------|-------------------------|
| Framework             | Spring boot             |
| Framework version     | 2.7.1                   |
| Build tool            | Maven                   |
| IDE                   | Jetbrains IntelliJ IDEA |
| Database              | H2 database             |
| Template Engine       | Thymeleaf               |
| External Dependencies | Lombok, ModelMapper     |
| Hashing Algorithm     | SHA-256                 |


## Instructions
### Prerequisites 
- Java JDK (Version 17)
- IntelliJ IDEA
- Maven 
### Steps
- Open IntelliJ IDEA and select "Open" from the file menu. Navigate to the project directory and select the pom.xml file. Click "Open" to import the project.
- In IntelliJ, open the Maven tool window (usually on the right side). Navigate to Lifecycle and run the clean and install goals.
- Run the main application class (FoodStoreApplication.java) in IntelliJ IDEA. 
- The console part of the project is now running.
- Open a web browser and go to http://localhost:8080 to access the web application.
- The [seeded login credntials](#seeded-login-credntials) can be used to access authorized requests.

## Seeded Login Credntials
### Customer
| SN | Username  | Password  |
|----|-----------|-----------|
| 1. | customer1 | customer1 |
| 2. | customer2 | customer2 |

### Administrator
| SN | Username | Password |
|----|----------|----------|
| 1. | admin1   | admin1   |
| 2. | admin2   | admin2   |



## Assignment Template Form / Implementation Checklist
| Food product class implemented |
|--------------------------------|
- [x] Food product class fully implemented and tested

| Customer class implemented |
|----------------------------|
- [x] Customer Class fully implemented and tested, including the address class

| Menu Options for Customers to: |
|--------------------------------|
- [x] List all customers in the system
- [x] Add a customer to the system
- [x] Find a customer by ID
- [x] Update a customer
- [x] Delete a customer from the system

| Menu Options for Products |
|---------------------------|
- [x] List all products in the system
- [x] Add a product to the system
- [x] Find a product by ID
- [x] Update a product by ID
- [x] Delete a product by ID

| Web Functions for Customers |
|-----------------------------|
- [x] List all customers in the system
- [x] Add a customer to the system
- [x] Edit a customer in the system
- [x] Delete a customer from the system

| Web Functions for Products |
|----------------------------|
- [x] List all products in the system
- [x] Add and edit a product in the system
- [x] Delete a product from the system

| Shopping Basket |
|-----------------|
- [x] Search descriptions and/or filter for products

| Additional Features |
|---------------------|
- [x] Lambda expressions
- [x] Unit Testing
- [x] JavaDoc
- [x] Design Patterns (Builder Pattern, Repository Pattern, Proxy Pattern, Singleton, Factory method)
- [x] Data Seeding
- [x] Hashing Algorithm (SHA-256)
- [x] Use of Optional

| Optional Features |
|-------------------|
- [x] Food item class with expiry date
- [x] Search descriptions and/or filter for expired items

## Licenses
- Most images used in this project are gotten from [pixabay](https://www.pixabay.com) and under the Free Domain LICENSE.
- 