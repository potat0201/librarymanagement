package library.model;

import java.time.LocalDate;

public class Book {

    private long id;
    private String title;
    private String isbn;
    private String description;
    private LocalDate publishDate;
    private long publisherId;   // map với cột publisher_id trong DB
    private String status;      // nếu chưa dùng cũng không sao

    // ⚠️ Constructor rỗng – BẮT BUỘC để dùng new Book()
    public Book() {
    }

    // Constructor đầy đủ (BookDAO dùng cái này để new Book từ ResultSet)
    public Book(long id,
                String title,
                String isbn,
                String description,
                LocalDate publishDate,
                long publisherId,
                String status) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.description = description;
        this.publishDate = publishDate;
        this.publisherId = publisherId;
        this.status = status;
    }

    // Có thể thêm 1 constructor không có status nếu bạn thích:
    public Book(long id,
                String title,
                String isbn,
                String description,
                LocalDate publishDate,
                long publisherId) {
        this(id, title, isbn, description, publishDate, publisherId, null);
    }

    // ====== Getter & Setter ======
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    public long getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(long publisherId) {
        this.publisherId = publisherId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", isbn='" + isbn + '\'' +
                ", publishDate=" + publishDate +
                ", publisherId=" + publisherId +
                ", status='" + status + '\'' +
                '}';
    }
}
