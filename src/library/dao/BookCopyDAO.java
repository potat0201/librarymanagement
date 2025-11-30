package library.dao;

import library.database.DatabaseConnection;
import library.model.BookCopy;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookCopyDAO {

    public List<BookCopy> getCopiesByBookId(long bookId) {
        List<BookCopy> list = new ArrayList<>();
        String sql = "SELECT copy_id, book_id, location, status FROM book_copy WHERE book_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, bookId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new BookCopy(
                    rs.getLong("copy_id"),
                    rs.getLong("book_id"),
                    rs.getString("location"),
                    rs.getString("status")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public BookCopy getCopyById(long copyId) {
        String sql = "SELECT copy_id, book_id, location, status FROM book_copy WHERE copy_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, copyId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new BookCopy(
                    rs.getLong("copy_id"),
                    rs.getLong("book_id"),
                    rs.getString("location"),
                    rs.getString("status")
                );
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    public boolean updateStatus(long copyId, String status) {
        String sql = "UPDATE book_copy SET status = ? WHERE copy_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setLong(2, copyId);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public BookCopy getAvailableCopy(long bookId) {
        String sql = "SELECT copy_id, book_id, location, status FROM book_copy WHERE book_id = ? AND status = 'Available' LIMIT 1";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, bookId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new BookCopy(
                    rs.getLong("copy_id"),
                    rs.getLong("book_id"),
                    rs.getString("location"),
                    rs.getString("status")
                );
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    // === HÀM MỚI CHO ADMIN ===
    public int countTotalCopies(long bookId) {
        String sql = "SELECT COUNT(*) FROM book_copy WHERE book_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, bookId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    public void addCopies(long bookId, int quantity) {
        String sql = "INSERT INTO book_copy (book_id, location, status) VALUES (?, 'Kho', 'Available')";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (int i = 0; i < quantity; i++) {
                stmt.setLong(1, bookId);
                stmt.executeUpdate();
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
}