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
- User Data Setup Sotring.
- Order Setup, Order Completion and Order Status Integration.

## Future Plans 
- Deployment on Heroku or Google.
- Enhancement to old Codebase.
- Add Feature Brands Category

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

#### Get all Users

```http
  GET /v1/users
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `api_key` | `string` | **Not Required**. Your API key |

#### Get Single User

```http
  GET /v1/users/{id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. Id of item to fetch |

#### Create a New User

```http
  POST /v1/users
```

#### Login

```http
  GET /v1/login
```

#### Delete Single User

```http
  DELETE /v1/users/{id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. Id of users to delete data |

#### Update Single User

```http
  PUT /v1/users/{id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. Id of users to update data |


## Categories

#### Create New Category

```http
  POST /v1/categories
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `api-key`      | `string` | **Not Required**. api-key for authentication |

#### Get All Categories

```http
  GET /v1/categories
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `api-key`      | `string` | **Not Required**. api-key for authentication |

#### Get Category By ID

```http
  GET /v1/categories/{id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. Id of Category |

#### DELETE Category By ID

```http
  DELETE /v1/categories/{id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. Id of Category |

### Update Category
```http
  PUT /v1/categories/{id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. Id of Category |

## Products

### Create New Product
```http
  POST /v1/products
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `api-key`      | `string` | **Required**. api-key for Authentication |

### Get Product
```http
  GET /v1/products/{id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. id for products |

### DELETE Product
```http
  DELETE /v1/products/{id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. id for products |

### Update Product
```http
  PUT /v1/products/{id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. id for products |


## Promotions Cards

### Create New Promotions
```http
  POST /v1/promotions
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `api-key`      | `string` | **Required**. api-key for Authentication |

### Get All Promotions
```http
  GET /v1/promotions
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `api-key`      | `string` | **Required**. api-key for Authentication |

### Get Single Promotion
```http
  GET /v1/promotions/{id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. id for promotion |


### Delete Promotion
```http
  DELETE /v1/promotions/{id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. id for promotion |

### Update Promotion
```http
  PUT /v1/promotions/{id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. id for promotion |


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

**Stargazers**

[![Stargazers repo roster for @KhubaibKhan4/Flexi-Store-Server](http://reporoster.com/stars/dark/KhubaibKhan4/Flexi-Store-Server)](https://github.com/KhubaibKhan4/Flexi-Store-Server/stargazers)

**Forkers**

[![Forkers repo roster for @KhubaibKhan4/Flexi-Store-Server](http://reporoster.com/forks/dark/KhubaibKhan4/Flexi-Store-Server)](https://github.com/KhubaibKhan4/Flexi-Store-Server/network/members)

# Contributing

Contributions are welcome! Please check the CONTRIBUTING.md file for guidelines.

# License

This project is licensed under the MIT License - see the LICENSE file for details.
