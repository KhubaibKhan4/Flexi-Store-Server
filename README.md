# Flexi-Store-Server

Flexi Store is an Ecommerce Application Server.

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Screenshots](#screenshots)
- [Contributing](#contributing)
- [License](#license)

## Introduction

Flexi Store is a server-side application designed to support ecommerce functionalities. It provides a robust backend system for managing users, categories, and products, facilitating the development of ecommerce platforms.

## Features

- User authentication and authorization
- CRUD operations for managing users, categories, and products
- Secure API endpoints for interacting with the server
- Flexible and scalable architecture for accommodating future enhancements

## Installation

To install Flexi Store Server locally, follow these steps:

1. Clone the repository:

   ```bash
   git clone https://github.com/your-username/flexi-store-server.git
2. Navigate to the project directory:
   ```
   cd flexi-store-server
3. Install dependencies:
  ```
   ./gradlew build
```
4. Start the server:
   ```
   ./gradlew run
    ```
# API Endpoints

## Users

- **POST /v1/users**: Create a new user.
- **POST /v1/login**: Authenticate user login.
- **GET /v1/users**: Get all users.
- **GET /v1/users/{id}**: Get user by ID.
- **DELETE /v1/users/{id}**: Delete user by ID.
- **PUT /v1/users/{id}**: Update user by ID.

## Categories

- **POST /v1/categories**: Create a new category.
- **GET /v1/categories**: Get all categories.
- **GET /v1/categories/{id}**: Get category by ID.
- **DELETE /v1/categories/{id}**: Delete category by ID.
- **PUT /v1/categories/{id}**: Update category by ID.

## Products

- **POST /v1/products**: Create a new product.
- **GET /v1/products**: Get all products.
- **GET /v1/products/{id}**: Get product by ID.
- **DELETE /v1/products/{id}**: Delete product by ID.
- **PUT /v1/products/{id}**: Update product by ID.

# Screenshots

Add screenshots of your application here.

# Contributing

Contributions are welcome! Please check the CONTRIBUTING.md file for guidelines.

# License

This project is licensed under the MIT License - see the LICENSE file for details.



   
