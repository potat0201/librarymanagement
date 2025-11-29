package library.dao;

import library.database.DatabaseConnection;
import library.model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    public List<Book> getAllBooks() {
        List<Book> list = new ArrayList<>();

        String sql = """
                SELECT book_id, title, isbn, description, publish_date, publisher_id
                FROM book
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Book b = new Book(
                    rs.getLong("book_id"),
                    rs.getString("title"),
                    rs.getString("isbn"),
                    rs.getString("description"),
                    rs.getDate("publish_date").toLocalDate(),
                    rs.getLong("publisher_id")
                );

                list.add(b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    public Book getBookById(long id) {
    String sql = "SELECT * FROM book WHERE book_id = ?";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setLong(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return new Book(
                    rs.getLong("book_id"),
                    rs.getString("title"),
                    rs.getString("isbn"),
                    rs.getString("description"),
                    rs.getDate("publish_date").toLocalDate(),
                    rs.getLong("publisher_id")
            );
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}

public boolean addBook(Book b) {
    String sql = "INSERT INTO book (title, isbn, description, publish_date, publisher_id) VALUES (?, ?, ?, ?, ?)";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, b.getTitle());
        stmt.setString(2, b.getIsbn());
        stmt.setString(3, b.getDescription());
        stmt.setDate(4, java.sql.Date.valueOf(b.getPublishDate()));
        stmt.setLong(5, b.getPublisherId());

        return stmt.executeUpdate() > 0;

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}

public boolean updateBook(Book b) {
    String sql = "UPDATE book SET title=?, isbn=?, description=?, publish_date=?, publisher_id=? WHERE book_id=?";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, b.getTitle());
        stmt.setString(2, b.getIsbn());
        stmt.setString(3, b.getDescription());
        stmt.setDate(4, java.sql.Date.valueOf(b.getPublishDate()));
        stmt.setLong(5, b.getPublisherId());
        stmt.setLong(6, b.getId());

        return stmt.executeUpdate() > 0;

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}

public boolean deleteBook(long id) {
    String sql = "DELETE FROM book WHERE book_id=?";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setLong(1, id);
        return stmt.executeUpdate() > 0;

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}

}
