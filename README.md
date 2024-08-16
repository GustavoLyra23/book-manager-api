# Book Manager API

## 🌟 Introduction

Welcome to the **Book Manager API**—a state-of-the-art backend service meticulously crafted for managing library book catalogs with precision and efficiency. Built on the robust foundations of Java 21 and Spring Boot, this API offers a comprehensive suite of features tailored to meet the needs of modern software systems. Whether you're integrating it into a large-scale enterprise application or using it as a standalone service, the Book Manager API is designed to be scalable, secure, and incredibly user-friendly.

## Key Features

- **Cutting-Edge Technology Stack**: Built with Java 21 and the latest Spring Boot release, ensuring compatibility with modern Java features and the Spring ecosystem.
- **Secure JWT Authentication**: Implements industry-standard JWT tokens to secure endpoints, ensuring that only authenticated and authorized users can access sensitive data.
- **RESTful API Design**: Adheres to REST principles, providing a scalable and flexible architecture suitable for integration into any system.
- **Interactive API Documentation**: Explore and interact with the API using [Swagger UI](http://localhost:8080/swagger-ui/index.html), a comprehensive tool for API visualization.
- **H2 In-Memory Database**: Simplifies testing and development with a lightweight, in-memory H2 database, accessible via the [H2 Console](http://localhost:8080/h2-console).
- **FTP Integration**: Seamlessly exports data files to a remote FTP server, making it easier to manage external data flows.
- **Detailed Postman Collection**: Includes a comprehensive set of pre-configured requests, streamlining the testing process and ensuring a smooth development experience.

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
cd book-manager-api
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

Once the container is running, the API will be accessible at `http://localhost:8080`.
