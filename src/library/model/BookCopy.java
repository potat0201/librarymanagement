package library.model;

public class BookCopy {
    private long copyId;
    private long bookId;
    private String location;
    private String status;

    public BookCopy() {}

    public BookCopy(long copyId, long bookId, String location, String status) {
        this.copyId = copyId;
        this.bookId = bookId;
        this.location = location;
        this.status = status;
    }

    public long getCopyId() { return copyId; }
    public void setCopyId(long copyId) { this.copyId = copyId; }

    public long getBookId() { return bookId; }
    public void setBookId(long bookId) { this.bookId = bookId; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public boolean isAvailable() {
        return "Available".equalsIgnoreCase(status);
    }

    @Override
    public String toString() {
        return "CopyID: " + copyId + ", BookID: " + bookId + ", Location: " + location + ", Status: " + status;
    }
}