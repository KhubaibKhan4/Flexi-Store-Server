# Flexi-Store-Server

Flexi Store is an Ecommerce Application Server designed to provide a flexible and scalable backend for online stores.

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

## Future Plans 
- Deployment on Heroku or Google.
- Enhancement to old Codebase.

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
## Setup
Remember you need to setup the Flexi-Store Server to use the Flexi-Store Applications. It's mandatory, otherwise it will not work at all. To Setup, you need to clone this on your local device. Open it in the Intellij IDEA. 
After Cloning the project, You need to download the `PG Admin 4` from there official website and install it. After Installing that, You need to create a data with any name. Now, open the Intellij IDEA where your cloning project is running, Click on `Edit / Run Configuration > Environment Variables >`, here you need to add two values inside the variables. `JDBC_DATABASE_URL= jdbc:postgresql:databaseName?user=postgres&password=yourpass` & `JDBC_DRIVER= org.postgresql.Driver`. After Adding these into your Environment Variables, You are good to go. To Use this Server inside your Application Locally, You need to simply get the local ip. For that you need to `Terminal` or `Command Prompt`. Get the Local IP and replace the base URL `kotlin/utils/Constant.kt` & URL `http://your_ip:8080/`. If you still gets any error, Please create an issue here.

## Flexi-Store-KMP

Flexi-Store is developed using Ktor and is mandatory for the backend of the Flexi-Store-KMP. You can find the Flexi-Store-KMP repository [here](https://github.com/KhubaibKhan4/Flexi-Store-KMP). 

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

## Promotions Cards

- **POST /v1/promotions**: Create a new promotions product.
- **GET /v1/promotions**: Get all promotions products.
- **GET /v1/promotions/{id}**: Get promotions product by ID.
- **DELETE /v1/promotions/{id}**: Delete promotions product by ID.
- **PUT /v1/promotions/{id}**: Update promotions product by ID.

  ## 💰 You can help me by Donating
  [![BuyMeACoffee](https://img.shields.io/badge/Buy%20Me%20a%20Coffee-ffdd00?style=for-the-badge&logo=buy-me-a-coffee&logoColor=black)](https://buymeacoffee.com/khubaibkhan) [![PayPal](https://img.shields.io/badge/PayPal-00457C?style=for-the-badge&logo=paypal&logoColor=white)](https://paypal.me/18.bscs) [![Patreon](https://img.shields.io/badge/Patreon-F96854?style=for-the-badge&logo=patreon&logoColor=white)](https://patreon.com/MuhammadKhubaibImtiaz) [![Ko-Fi](https://img.shields.io/badge/Ko--fi-F16061?style=for-the-badge&logo=ko-fi&logoColor=white)](https://ko-fi.com/muhammadkhubaibimtiaz) 

# Screenshots

![Screenshot 1](https://github.com/KhubaibKhan4/Flexi-Store-Server/blob/master/assests/screenshots/1.png)
![Screenshot 2](https://github.com/KhubaibKhan4/Flexi-Store-Server/blob/master/assests/screenshots/2.png)
![Screenshot 3](https://github.com/KhubaibKhan4/Flexi-Store-Server/blob/master/assests/screenshots/3.png)
![Screenshot 4](https://github.com/KhubaibKhan4/Flexi-Store-Server/blob/master/assests/screenshots/4.png)
![Screenshot 5](https://github.com/KhubaibKhan4/Flexi-Store-Server/blob/master/assests/screenshots/5.png)
![Screenshot 6](https://github.com/KhubaibKhan4/Flexi-Store-Server/blob/master/assests/screenshots/6.png)
![Screenshot 7](https://github.com/KhubaibKhan4/Flexi-Store-Server/blob/master/assests/screenshots/7.png)
![Screenshot 8](https://github.com/KhubaibKhan4/Flexi-Store-Server/blob/master/assests/screenshots/8.png)
![Screenshot 9](https://github.com/KhubaibKhan4/Flexi-Store-Server/blob/master/assests/screenshots/9.png)
![Screenshot 10](https://github.com/KhubaibKhan4/Flexi-Store-Server/blob/master/assests/screenshots/10.png)
![Screenshot 11](https://github.com/KhubaibKhan4/Flexi-Store-Server/blob/master/assests/screenshots/11.png)

# Contributing

Contributions are welcome! Please check the CONTRIBUTING.md file for guidelines.

# License

This project is licensed under the MIT License - see the LICENSE file for details.
