# Restaurant Ordering System Development Prompt

## 1. Project Requirements
- **Core Functionality**: 
  - QR-based ordering system with table management
  - Point accumulation system (10% of total bill)
  - Order status tracking (Pending/Preparing/Ready)
  - Admin dashboard for menu/status management

## 2. Technical Specifications
### Stack
- **Backend**: Spring MVC + JPA/Hibernate (Java 17)
- **Database**: SQL Server
- **Frontend**: Thymeleaf + Bootstrap 5 (server-rendered)
- **Performance**: 
  - Implement skeleton screens for all data-loading states <button class="citation-flag" data-index="7">
  - Add subtle CSS animations for wait states (≤200ms duration)
  - Optimize SQL queries for <500ms response time

## 3. Database Schema
```sql
-- Tables
CREATE TABLE restaurant_table (
    id INT PRIMARY KEY IDENTITY(1,1),
    status NVARCHAR(50) DEFAULT 'AVAILABLE',
    created_at DATETIME DEFAULT GETDATE()
);

CREATE TABLE menu_item (
    id INT PRIMARY KEY IDENTITY(1,1),
    name NVARCHAR(255) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    category NVARCHAR(50) NOT NULL,
    status NVARCHAR(50) DEFAULT 'AVAILABLE'
);

CREATE TABLE customer (
    id INT PRIMARY KEY IDENTITY(1,1),
    phone_number NVARCHAR(20) UNIQUE NOT NULL,
    points_balance INT DEFAULT 0,
    tier NVARCHAR(20) DEFAULT 'REGULAR'
);

CREATE TABLE restaurant_order (
    id INT PRIMARY KEY IDENTITY(1,1),
    table_id INT NOT NULL,
    customer_id INT,
    status NVARCHAR(50) DEFAULT 'PENDING',
    total_amount DECIMAL(10,2),
    points_earned INT DEFAULT 0,
    points_used INT DEFAULT 0,
    created_at DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (table_id) REFERENCES restaurant_table(id),
    FOREIGN KEY (customer_id) REFERENCES customer(id)
);

CREATE TABLE order_item (
    id INT PRIMARY KEY IDENTITY(1,1),
    order_id INT NOT NULL,
    menu_item_id INT NOT NULL,
    quantity INT NOT NULL,
    FOREIGN KEY (order_id) REFERENCES restaurant_order(id),
    FOREIGN KEY (menu_item_id) REFERENCES menu_item(id)
);
```

## 4. Task Breakdown
### Core Features
1. **Order Management**
   - Implement session-based cart with quantity validation
   - Create order status tracking system
   - Add point calculation system (10% of total)
   - Integrate ethical friction: 
     - 2-second delay before order cancellation
     - Confirmation modal for critical actions [[3]]

2. **Frontend Optimization**
   - Implement skeleton screens for menu loading
   - Add CSS fade-in animations for order status updates
   - Create progressive onboarding with progress bar
   - Maintain consistent terminology (Use "Cancel" instead of "Delete")

3. **Admin Dashboard**
   - Create table status visualization (color-coded)
   - Implement bulk menu item status updates
   - Add sales analytics with time-based filtering

## 5. Design Principles
- **Performance**: 
  - All API responses must include loading states
  - Optimize image sizes (<100KB for menu items)
- **Consistency**:
  - Maintain uniform button styles across all pages
  - Use consistent error message formatting
- **Ethical Design**:
  - Add 1-second delay before order finalization
  - Include undo option for accidental deletions

## 6. Validation Rules
- Phone number: 10-12 digits with optional '+'
- Order time restriction: 10:00 PM cutoff
- Minimum order value: 50,000 VND

## 7. Security
- SQL injection prevention via JPA parameters
- Sensitive data encryption (phone numbers)
- Role-based access control (admin/staff)

Dưới đây là bản mô tả chi tiết cách hoạt động của hệ thống **không dùng QR code**, chỉ dùng query parameter để xác định bàn:

---

### **Cách hoạt động của hệ thống**

#### **1. Truy cập hệ thống**
- **URL truy cập**: 
  - Khách hàng vào link `http://localhost:8080/menu?table=1` (thay `1` bằng số bàn).
  - Nếu bàn chưa tồn tại → Tự động tạo mới với trạng thái **AVAILABLE**.
  - Nếu bàn đang **OCCUPIED** → Hiển thị thông báo "Bàn đang được sử dụng".

#### **2. Quy trình đặt hàng**
1. **Thêm món**:
   - Khách chọn món từ danh sách → Thêm vào giỏ (session-based).
   - Giỏ hàng lưu thông tin: `table_id`, `menu_item_id`, `quantity`.
2. **Chỉnh sửa/Xóa**:
   - Chỉ được sửa/xóa món khi order đang ở trạng thái **PENDING**.
3. **Thanh toán**:
   - Nhập SĐT → Hệ thống kiểm tra/tạo khách hàng.
   - Tính điểm tích lũy (10% tổng bill) → Cập nhật vào `customer.points_balance`.
   - Trạng thái bàn chuyển về **AVAILABLE**.

#### **3. Xử lý nghiệp vụ**
- **Giới hạn thời gian**:
  - Không cho phép đặt món sau 22:00 → Hiển thị thông báo "Nhà hàng đã đóng cửa".
- **Validation**:
  - SĐT phải có 10 chữ số.
  - Đơn hàng tối thiểu 50,000 VND.

#### **4. Admin Dashboard**
- **Quản lý bàn**:
  - Danh sách bàn với trạng thái màu sắc:
    - Xanh: **AVAILABLE**.
    - Đỏ: **OCCUPIED**.
- **Cập nhật món**:
  - Toggle trạng thái món (Available/Unavailable).
  - Chỉnh sửa giá/giảm giá.

---

### **Các trường hợp đặc biệt**
3. **Món hết hàng**:
   - Món có `status = UNAVAILABLE` → Ẩn khỏi menu.


## Database Configuration
Server: localhost
Database: restaurant_db
Username: sa
Password: Way113113@@
Port: 1433 (mặc định SQL Server)
Connection String
jdbc:sqlserver://localhost:1433;databaseName=restaurant_db;encrypt=true;trustServerCertificate=true;

---
