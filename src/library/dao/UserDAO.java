package library.dao;

import library.database.DatabaseConnection;
import library.model.Role;
import library.model.UserAccount;

import java.sql.*;

public class UserDAO {

    public UserAccount login(String username, String password) {

        String sql = """
                SELECT u.user_id,
                       u.username,
                       u.password_hash,
                       u.email,
                       u.status,
                       r.role_id,
                       r.role_name
                FROM user_account u
                JOIN role r ON u.role_id = r.role_id
                WHERE u.username = ? AND u.password_hash = ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new UserAccount(
                        rs.getLong("user_id"),
                        rs.getString("username"),
                        rs.getString("password_hash"),
                        rs.getString("email"),
                        rs.getString("status"),
                        new Role(
                                rs.getLong("role_id"),
                                rs.getString("role_name")
                        )
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    public boolean register(UserAccount user) {

        String sql = """
                INSERT INTO user_account (username, password_hash, email, status, role_id)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getStatus());
            stmt.setLong(5, user.getRole().getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
