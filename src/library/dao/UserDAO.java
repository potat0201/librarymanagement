package library.dao;

import library.database.DatabaseConnection;
import library.model.UserAccount;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public UserAccount login(String username, String passwordHash) {
        String sql = "SELECT u.user_id, u.username, u.password_hash, u.email, u.status, r.role_id, r.role_name FROM user_account u JOIN role r ON u.role_id = r.role_id WHERE u.username = ? AND u.password_hash = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, passwordHash);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new UserAccount(
                    rs.getLong("user_id"), rs.getString("username"), rs.getString("password_hash"),
                    rs.getString("email"), rs.getString("status"), rs.getInt("role_id"), rs.getString("role_name")
                );
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    // === SỬA LẠI: ROLE MEMBER ID LÀ 2 ===
    public boolean register(UserAccount u) {
        // Thay số 3 thành số 2
        String sql = "INSERT INTO user_account(username, password_hash, email, status, role_id) VALUES (?, ?, ?, 'Active', 2)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, u.getUsername());
            stmt.setString(2, u.getPasswordHash());
            stmt.setString(3, u.getEmail());
            return stmt.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public UserAccount getByUsername(String username) {
        String sql = "SELECT u.user_id, u.username, u.password_hash, u.email, u.status, r.role_id, r.role_name FROM user_account u JOIN role r ON u.role_id = r.role_id WHERE u.username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new UserAccount(
                    rs.getLong("user_id"), rs.getString("username"), rs.getString("password_hash"),
                    rs.getString("email"), rs.getString("status"), rs.getInt("role_id"), rs.getString("role_name")
                );
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    // === SỬA LẠI: ROLE MEMBER ID LÀ 2 ===
    public List<UserAccount> getAllMembers() {
        List<UserAccount> list = new ArrayList<>();
        // Thay role_id = 3 thành role_id = 2
        String sql = "SELECT u.user_id, u.username, u.email, u.status, r.role_name FROM user_account u JOIN role r ON u.role_id = r.role_id WHERE r.role_name = 'Member' OR u.role_id = 2";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                UserAccount u = new UserAccount();
                u.setId(rs.getLong("user_id"));
                u.setUsername(rs.getString("username"));
                u.setEmail(rs.getString("email"));
                u.setStatus(rs.getString("status"));
                u.setRoleName(rs.getString("role_name"));
                list.add(u);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
}