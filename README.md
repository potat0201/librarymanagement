# OOP Project - Library Management System

> **Đồ án môn học Lập trình Hướng đối tượng (OOP)**
>


---
**Mô tả dự án: https://docs.google.com/document/d/1ZETEJOZ2ViOiJ-UOE1qh0LP3ZKeSlvJBWaJC2GaMPhA/edit?usp=sharing**

---

## Mục lục
1. Giới thiệu
2. Cấu trúc dự án
3. Tính năng & Yêu cầu chức năng
4. Các Class chính
5. Hướng dẫn cài đặt

---

## Giới thiệu

Hệ thống Quản lý Thư viện là ứng dụng Desktop được xây dựng nhằm giải quyết các bài toán quản lý thủ công tại thư viện. Hệ thống giúp tự động hóa quy trình mượn trả, kiểm soát kho sách và quản lý thông tin độc giả một cách chính xác.

**Điểm nổi bật:**
* Áp dụng kiến trúc **3-Tier** (GUI - Service - DAO).
* Tuân thủ chặt chẽ 4 tính chất **OOP**: Đóng gói, Kế thừa, Đa hình, Trừu tượng.
* Giao diện thân thiện, dễ sử dụng.

---

## Cấu trúc dự án

Dự án được tổ chức theo các package chức năng:

* `src/library/model`: Chứa các thực thể (Entity) ánh xạ với bảng CSDL.
* `src/library/dao`: (Data Access Object) Xử lý truy vấn SQL trực tiếp.
* `src/library/service`: Xử lý nghiệp vụ logic (Business Logic).
* `src/library/gui`: Chứa các giao diện người dùng (JFrame, JPanel).
* `src/library/database`: Cấu hình kết nối MySQL.
* `src/library/exception`: Các class xử lý ngoại lệ tùy chỉnh.

---

## Tính năng & Yêu cầu chức năng

### 1. Giao diện Quản lý (Dành cho Admin)
Đăng nhập với quyền Admin để truy cập **AdminMenuGUI**:

* **Quản lý Sách (BookCRUDGUI):**
    * Xem danh sách đầu sách.
    * Thêm mới đầu sách và nhập số lượng bản in (BookCopy) vào kho.
    * Cập nhật thông tin hoặc xóa sách.
* **Quản lý Mượn/Trả (BorrowReturnAdminGUI):**
    * Xem danh sách các phiếu mượn đang hoạt động.
    * Xác nhận độc giả trả sách (Cập nhật trạng thái kho & phiếu mượn).
* **Quản lý Độc giả (UserManagementGUI):**
    * Xem danh sách thành viên và trạng thái tài khoản.

### 2. Giao diện Độc giả (Dành cho User/Member)
Đăng nhập/Đăng ký với quyền Member để truy cập **UserMenuGUI**:

* **Tìm kiếm & Mượn sách (BookListGUI):**
    * Tìm sách theo tên.
    * Xem tình trạng "Khả dụng" (Còn sách trong kho hay không).
    * Thực hiện mượn sách trực tuyến.
* **Lịch sử mượn (BorrowHistoryGUI):**
    * Xem lại lịch sử mượn trả cá nhân.
    * Theo dõi ngày mượn, ngày trả và trạng thái (Borrowed/Returned).

---

## Các Class chính

### Core Classes (Entities - Model)
Các lớp này kế thừa từ `BaseEntity` (trừ các class phụ trợ):

1.  **UserAccount**: Đại diện cho tài khoản người dùng (Username, PasswordHash, Email, Role, Status).
2.  **Book**: Chứa thông tin đầu sách (Title, ISBN, Description, PublishDate).
3.  **BookCopy**: Đại diện cho cuốn sách vật lý trong kho (CopyID, Location, Status: Available/Borrowed).
4.  **Loan**: Phiếu mượn sách (MemberID, StaffID, LoanDate, DueDate, ReturnDate, Fee).
5.  **LoanDetail**: Chi tiết phiếu mượn, liên kết giữa `Loan` và `BookCopy`.

### Service & Management Classes

1.  **UserServiceDB** (`src/library/service/UserServiceDB.java`):
    * Xử lý đăng nhập, kiểm tra phân quyền.
    * Đăng ký tài khoản mới.

2.  **BookService** (`src/library/service/BookService.java`):
    * Cung cấp chức năng tìm kiếm sách.
    * Lấy danh sách sách hiển thị lên GUI.

3.  **LoanService** (`src/library/service/LoanService.java`):
    * `borrowBook()`: Xử lý nghiệp vụ mượn (Kiểm tra kho -> Tạo Loan -> Cập nhật trạng thái sách).
    * `returnBook()`: Xử lý trả sách (Cập nhật ngày trả -> Mở lại trạng thái sách Available).
    * `calculateFee()`: Tính phí mượn (Sử dụng Interface `IFeePolicy`).

4.  **Interface IFeePolicy** (`src/library/service/IFeePolicy.java`):
    * Định nghĩa phương thức tính phí, áp dụng Đa hình cho các chính sách phí khác nhau (ví dụ: `StandardFeePolicy`).

---

##  Hướng dẫn cài đặt

1.  **Cài đặt Database:**
    * Mở MySQL Workbench (hoặc tool tương tự).
    * Tạo schema `csdl`.
    * Chạy script SQL để tạo bảng (`user_account`, `book`, `book_copy`, `loan`...) và dữ liệu mẫu.

2.  **Cấu hình kết nối:**
    * Mở file `src/library/database/DatabaseConnection.java`.
    * Sửa `USER` và `PASS` tương ứng với MySQL của bạn.

3.  **Chạy ứng dụng:**
    * Chạy file `src/App.java`.
    * Đăng nhập bằng tài khoản Admin hoặc đăng ký tài khoản mới để trải nghiệm.