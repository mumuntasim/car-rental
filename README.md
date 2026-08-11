# 🚗 Car Rental System

**Car Rental System** is a web-based platform for renting and managing vehicles, developed using **Java and Spring Boot**. The application provides users with the ability to register, authenticate, browse available vehicles, rent vehicles, and return them.

The project follows a **microservice-based architecture**, where a separate microservice is responsible for checking the validity of the technical inspection of vehicles before they can be rented.

## 🚀 Key Features

* **Authentication System:** Secure user registration and login using Spring Security and BCrypt password hashing.
* **Automatic Role Assignment:** The first registered user is automatically assigned the `ADMIN` role. All subsequent registered users are assigned the `USER` role by default.
* **Role-Based Access Control:** Different functionalities are available depending on the user's role.
* **Vehicle Management:** Administrators can add and manage vehicles in the system.
* **Vehicle Catalog:** Users can browse all currently available vehicles.
* **Vehicle Rental:** Authenticated users can rent available vehicles.
* **Vehicle Return:** Rented vehicles are displayed in the user's profile and can be returned to the available vehicle catalog.
* **Microservice Integration:** Before a vehicle can be rented, the application automatically checks its technical inspection through an external microservice.
* **Server-Side Validation:** User input is validated on the server side to ensure data integrity and provide meaningful error messages.
* **Modern UI:** Clean and responsive user interface built with Thymeleaf, HTML5, and CSS3.

## 📄 Application Pages

The application consists of the following dynamic web pages:

1. **Home (Index)** – Landing page with navigation to the main application features.
2. **Login** – Authentication page for existing users.
3. **Register** – Registration form for creating a new user account.
4. **Cars Catalog** – Displays all currently available vehicles and provides the option to rent them.
5. **Add Car** – Administrator page for adding new vehicles to the system.
6. **My Profile** – Personal user page displaying rented vehicles and providing the option to return them.

## 🛠 Technology Stack

### Main Application

* **Backend:** Java, Spring Boot, Spring MVC, Spring Data JPA, Spring Security
* **Database:** MySQL
* **Frontend:** Thymeleaf, HTML5, CSS3
* **Security:** Spring Security, BCryptPasswordEncoder
* **Microservice Communication:** REST API
* **Build Tool:** Maven
* **Additional Tools:** Lombok

### Inspection Microservice

* **Java**
* **Spring Boot**
* **Spring Web**
* **REST API**
* **Maven**

## 📂 Domain Entities

### User

Represents a registered user of the system.

Each user has a role that determines their access level:

* `ADMIN` – assigned automatically to the first registered user.
* `USER` – assigned automatically to every subsequent registered user.

### Vehicle

Represents a vehicle available in the rental system and contains information such as:

* Brand
* Model
* Registration number
* Price per day
* Rental status (`isRented`)

### Inspection

Represents technical inspection information handled by the separate **Inspection Microservice**. The microservice is responsible for determining whether a vehicle has a valid technical inspection.

## 👤 User Roles and Access

### First Registered User – `ADMIN`

The first user who registers in the application is automatically assigned the `ADMIN` role.

The administrator can:

* Add new vehicles.
* Manage the vehicle fleet.
* Access the functionality available to regular users.

### Subsequent Registered Users – `USER`

All users registered after the first user automatically receive the `USER` role.

Regular users can:

* Browse available vehicles.
* Rent vehicles.
* View their rented vehicles.
* Return rented vehicles.

This approach ensures that the system has an initial administrator without requiring manual database configuration.

## 🔄 Microservice Architecture

The project consists of two separate Spring Boot applications:

```text
┌─────────────────────────────┐
│     Car Rental System       │
│                             │
│  Spring Boot Application    │
│  Thymeleaf + MySQL          │
└──────────────┬──────────────┘
               │
               │ REST API
               ▼
┌─────────────────────────────┐
│   Inspection Microservice   │
│                             │
│ Technical Inspection Check  │
└─────────────────────────────┘
```

When a user attempts to rent a vehicle, the main application sends a request to the **Inspection Microservice**.

The microservice checks whether the vehicle has a valid technical inspection and returns the result to the main application.

If the inspection is valid, the rental can proceed. Otherwise, the vehicle cannot be rented.

This architecture separates the technical inspection logic from the main application and allows the microservice to be developed, maintained, and deployed independently.

## 🔗 Repositories

### Main Application

https://github.com/mumuntasim/car-rental.git

### Inspection Microservice

https://github.com/mumuntasim/inspection-service.git

## 📋 Installation & Setup

### 1. Clone the Main Application


git clone https://github.com/mumuntasim/car-rental.git


### 2. Clone the Inspection Microservice


git clone https://github.com/mumuntasim/inspection-service.git


### 3. Configure the Database

Open:

```text
src/main/resources/application.properties
```

and configure your MySQL database credentials:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/car_rental
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password
```

### 4. Start the Inspection Microservice

Run the main Spring Boot class of the **Inspection Microservice** using your IDE or Maven.

### 5. Start the Car Rental Application

Run the main Spring Boot class of the **Car Rental System**.

The application will be available at:

```text
http://localhost:8080
```

## 🛡️ Security

### Guests

Unauthenticated visitors can:

* Access the home page.
* Browse the vehicle catalog.
* Register an account.
* Log in to the application.

### Users (`USER`)

Authenticated users can:

* Browse available vehicles.
* Rent vehicles.
* View their rented vehicles.
* Return rented vehicles.

### Administrators (`ADMIN`)

Administrators have access to administrative functionality and can:

* Add new vehicles.
* Manage the vehicle fleet.
* Use all functionality available to regular users.

### Password Security

User passwords are never stored as plain text. Passwords are securely hashed using **BCryptPasswordEncoder**.

## 🏗️ Application Architecture

The main application follows a layered architecture:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

Communication with the inspection microservice is handled through a REST API:

```text
User
  ↓
Car Rental Application
  ↓
Inspection Microservice
  ↓
Inspection Result
  ↓
Car Rental Application
  ↓
Rental Approved / Rejected
```

## 🎯 Project Goal

The main goal of the project is to demonstrate the development of a real-world web application using **Java, Spring Boot, Spring Security, Spring Data JPA, MySQL, Thymeleaf, and microservice architecture**.

The project demonstrates:

* User authentication and authorization.
* Automatic role assignment.
* Role-based access control.
* CRUD operations.
* Server-side validation.
* Vehicle rental and return functionality.
* Database persistence.
* REST API communication.
* Integration between a main application and an independent microservice.
