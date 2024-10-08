# Book Manager API
Welcome to the Book Manager API a meticulously crafted backend service designed for the efficient management of library book catalogs. Built on the robust foundations of Java 21 and Spring Boot, this API leverages the latest advancements in these technologies to offer a comprehensive suite of features tailored to the needs of modern software systems. Whether you're integrating it into a large-scale enterprise application or using it as a standalone service, the Book Manager API is designed to be scalable, secure, and user-friendly.

In addition to its core functionality, the API supports seamless FTP integration for exporting files in both Excel and TXT formats, allowing for flexible data handling and external reporting. The API’s RESTful architecture ensures that it can easily scale with your application, providing a reliable solution whether you manage a small collection or an extensive library network.

## Features

-  Java 21
-  Spring Boot 3.3.2
-  JWT Authentication
-  RESTful API Design 
-  Swagger
-  H2 In-Memory Database
-  FTP Integration
## Prerequisites

Before you start, ensure your environment meets the following requirements:

- **Java 21**: The latest version should be installed.
- **Docker**: Necessary for containerization and deployment.
- **Postman**: Recommended for API testing and interaction.

## Quickstart Guide

### 1. Clone the Repository

To get started, clone the repository to your local machine:

```bash
git clone https://github.com/GustavoLyra23/book-manager-api.git
```
after cloning go to the project directory
```bash
cd bookmanager
```

### 2. Build the Docker Image
Build the Docker image by running the following command:

```bash
docker build -t book-manager-api .
```
### 3. Run the Docker Container
Launch the API by executing:

```bash
docker run -p 8080:8080 book-manager-api
```

Once the container is running, the API will be accessible at http://localhost:8080.

## Accessing the API
- **Swagger UI:** Interact with the API documentation at http://localhost:8080/swagger-ui/index.html.
- **H2 Console:** Access the in-memory H2 database at http://localhost:8080/h2-console with the credentials:
-      Username: sa
       Password: 123456
 
## Postman Collection
### Importing the Collection
To facilitate testing, a Postman collection is included:

- Locate the Collection: Navigate to the postman directory within the repository.
- Import into Postman: Import the collection for immediate access to a comprehensive set of API requests.

### Using JWT with Postman
To test the secured endpoints:

- Obtain a JWT: Use the login endpoint in Postman to generate a JWT.
- Authorize Requests: Include the JWT in the Authorization header as a Bearer token when making API requests.

### Security and Authentication
Security is a paramount concern in the Book Manager API. We employ JWT tokens to secure the API, ensuring that sensitive data remains protected:

- Authentication: Clients authenticate by obtaining a JWT via the provided authentication endpoint.
- Authorization: Secure requests by including the JWT in the Authorization header as a Bearer token.
- This approach ensures that only authenticated and authorized users can perform critical operations, protecting the integrity and confidentiality of your data.

### Database Schema & Entity Relationships
The API’s database schema is thoughtfully designed with clear and efficient relationships:

- Entities
- Book: Represents a book within the system, containing essential attributes like title, author, and publication date.
- Book Family: Represents a collection of books categorized under the same family or series.
- Relationships
- One-to-Many (Book Family to Books): A BookFamily can include multiple Books, reflecting real-world categorization like series or collections.
- Many-to-One (Books to Book Family): Each Book is linked to a single BookFamily, ensuring structured and organized data storage.
- This relational structure is crucial for maintaining data integrity and enables efficient querying and data manipulation, perfectly suited for complex library systems.

### FTP Server Integration
For external file management, the API seamlessly integrates with an FTP server. To view files uploaded by the API:

- Download FileZilla Client: Install FileZilla or another FTP client of your choice.
- Connect to the FTP Server with the Server data:
 ### FTP config
    ftp:
     host: ftp.dlptest.com
     port: 21
     username: dlpuser
     password: rNrKYTX9g7z3RgJRmxWuGHbeu

This integration ensures that your exported files are easily accessible and manageable through a familiar FTP interface.
