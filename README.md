# Restaurant Ordering System

A web-based restaurant ordering system built with Spring Boot, Thymeleaf, and Bootstrap.

## Features

- **Table-based Ordering**: Customers can order food by accessing a URL with their table number
- **Menu Management**: Admin can add, edit, and update menu items
- **Order Tracking**: Order status updates (Pending, Preparing, Ready, Completed)
- **Points System**: Customers earn 10% of their order total as points
- **Admin Dashboard**: Comprehensive dashboard for restaurant staff to manage orders and tables

## Technology Stack

- **Backend**: Spring Boot 3.2.3 (Java 17)
- **Database**: SQL Server
- **Frontend**: Thymeleaf + Bootstrap 5 (server-rendered)
- **Build Tool**: Maven

## Getting Started

### Prerequisites

- Java 17 or higher
- SQL Server
- Maven

### Database Setup

1. Create a database named `restaurant_db` in SQL Server
2. Use the following database configuration:
   - Server: localhost
   - Database: restaurant_db
   - Username: sa
   - Password: Way113113@@
   - Port: 1433 (default SQL Server port)
   - Connection String: `jdbc:sqlserver://localhost:1433;databaseName=restaurant_db;encrypt=true;trustServerCertificate=true;`

### Running the Application

1. Clone the repository
2. Navigate to the project directory
3. Run the application using Maven:
   ```
   mvn spring-boot:run
   ```
4. Access the application at `http://localhost:8080`

## Usage

### Customer Interface

- Access the menu by navigating to `http://localhost:8080/menu?table={tableNumber}`
- Browse menu items, add them to cart, and place orders
- View order status and track progress
- Order status page refreshes automatically every 30 seconds

### Admin Interface

- Access the admin dashboard at `http://localhost:8080/admin`
- Manage tables, menu items, and orders
- Update order statuses and view sales data
- Refresh the dashboard manually to see the latest order statuses

## Implementation Notes

- The application uses server-side rendering with Thymeleaf templates
- No REST APIs or JavaScript-based data fetching is used for order status updates
- Order status updates are handled through page refreshes (automatic and manual)
- All data is retrieved directly from the database using Spring Data JPA

## Project Structure

- `src/main/java/com/restaurant/orderfood/model`: Entity classes
- `src/main/java/com/restaurant/orderfood/repository`: Data access layer
- `src/main/java/com/restaurant/orderfood/service`: Business logic
- `src/main/java/com/restaurant/orderfood/controller`: Web controllers
- `src/main/java/com/restaurant/orderfood/dto`: Data transfer objects
- `src/main/resources/templates`: Thymeleaf templates
- `src/main/resources/static`: Static resources (CSS, JS)

## License

This project is licensed under the MIT License - see the LICENSE file for details.
