package library.dao;

import library.database.DatabaseConnection;
import library.model.BookCopy;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookCopyDAO {

    // Lấy danh sách copy theo bookId
    public List<BookCopy> getCopiesByBookId(long bookId) {
        List<BookCopy> list = new ArrayList<>();
        String sql = "SELECT copy_id, book_id, import_id, location, status FROM book_copy WHERE book_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, bookId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                BookCopy c = new BookCopy(
                    rs.getLong("copy_id"),
                    rs.getLong("book_id"),
                    rs.getLong("import_id"),
                    rs.getString("location"),
                    rs.getString("status")
                );

                list.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy 1 copy theo copyId
    public BookCopy getCopyById(long copyId) {
        String sql = "SELECT copy_id, book_id, import_id, location, status FROM book_copy WHERE copy_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, copyId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new BookCopy(
                    rs.getLong("copy_id"),
                    rs.getLong("book_id"),
                    rs.getLong("import_id"),
                    rs.getString("location"),
                    rs.getString("status")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Cập nhật trạng thái copy
    public boolean updateStatus(long copyId, String status) {
        String sql = "UPDATE book_copy SET status = ? WHERE copy_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setLong(2, copyId);

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
