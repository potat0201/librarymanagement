package library.model;

import java.time.LocalDate;

public class Book extends BaseEntity {
    private String title;
    private String isbn;
    private String description;
    private LocalDate publishDate;

    public Book() {
        super();
    }

    public Book(long id, String title, String isbn, String description, LocalDate publishDate) {
        super(id); 
        this.title = title;
        this.isbn = isbn;
        this.description = description;
        this.publishDate = publishDate;
    }


    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getPublishDate() { return publishDate; }
    public void setPublishDate(LocalDate publishDate) { this.publishDate = publishDate; }

    @Override
    public String toString() {
        return title;
    }
}