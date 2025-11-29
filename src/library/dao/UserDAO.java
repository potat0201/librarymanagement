package library.dao;

import library.database.DatabaseConnection;
import library.model.UserAccount;

import java.sql.*;

public class UserDAO {

    // ===========================
    // LOGIN
    // ===========================
    public UserAccount login(String username, String passwordHash) {

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
            stmt.setString(2, passwordHash);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new UserAccount(
                        rs.getLong("user_id"),
                        rs.getString("username"),
                        rs.getString("password_hash"),
                        rs.getString("email"),
                        rs.getString("status"),
                        rs.getInt("role_id"),
                        rs.getString("role_name")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    // ===========================
    // REGISTER (Member)
    // ===========================
    public boolean register(UserAccount u) {

        String sql = """
                INSERT INTO user_account(username, password_hash, email, status, role_id)
                VALUES (?, ?, ?, 'Active', 3)
                """; // mặc định Member = role_id = 3

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, u.getUsername());
            stmt.setString(2, u.getPasswordHash());
            stmt.setString(3, u.getEmail());

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    // ===========================
    // GET USER BY USERNAME
    // ===========================
    public UserAccount getByUsername(String username) {
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
                WHERE u.username = ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new UserAccount(
                        rs.getLong("user_id"),
                        rs.getString("username"),
                        rs.getString("password_hash"),
                        rs.getString("email"),
                        rs.getString("status"),
                        rs.getInt("role_id"),
                        rs.getString("role_name")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    // ===========================
    // ADMIN: CREATE USER (mọi role)
    // ===========================
    public boolean createUser(UserAccount user) {

        String sql = """
                INSERT INTO user_account (username, password_hash, email, status, role_id)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPasswordHash());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getStatus());
            stmt.setInt(5, user.getRoleId());

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

}
