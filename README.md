# 🌦️ Weather API

Welcome to the Weather API project! This project provides a RESTful API to fetch weather information for various cities. It is built using Spring Boot and MongoDB.

## 🚀 Getting Started

These instructions will help you set up and run the project on your local machine for development and testing purposes.

## 📦 Prerequisites

- Java 23 or higher
- Maven 3.6.5 or higher
- Docker (for running MongoDB)

## 🛠️ Installation

1. **Clone the repository:**
  ```sh
  git clone https://github.com/Josepch1/weatherapi.git
  cd weatherapi
  ```

2. **Start MongoDB using Docker:**
  ```sh
  docker-compose up -d
  ```

3. **Build the project:**
  ```sh
  ./mvnw clean install
  ```

4. **Run the application:**
  ```sh
  ./mvnw spring-boot:run
  ```

## 📖 Usage

Once the application is running, you can access the API at `http://localhost:8080`.

## 🌐 API Endpoints

- `GET /api/cities` - Retrieve a list of cities and weathers
- `GET /api/cities/{city}` - Retrieve weather information for a specific city

Thank you for using the Weather API! 🌤️