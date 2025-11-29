package library.dao;

import library.database.DatabaseConnection;
import library.model.Loan;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoanDAO {

    // === CREATE LOAN ===
    public long createLoan(Loan loan) {
        String sql = """
                INSERT INTO loan (member_id, staff_id, loan_date, due_date, status, fee, paid)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setLong(1, loan.getMemberId());
            stmt.setLong(2, loan.getStaffId());
            stmt.setDate(3, Date.valueOf(loan.getLoanDate()));
            stmt.setDate(4, Date.valueOf(loan.getDueDate()));
            stmt.setString(5, loan.getStatus());
            stmt.setDouble(6, loan.getFee());
            stmt.setString(7, loan.getPaid());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) return rs.getLong(1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    // === GET LOAN BY ID ===
    public Loan getLoanById(long loanId) {
        String sql = "SELECT * FROM loan WHERE loan_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, loanId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Loan loan = new Loan();

                loan.setId(rs.getLong("loan_id"));
                loan.setMemberId(rs.getLong("member_id"));
                loan.setStaffId(rs.getLong("staff_id"));
                loan.setLoanDate(rs.getDate("loan_date").toLocalDate());
                loan.setDueDate(rs.getDate("due_date").toLocalDate());

                Date r = rs.getDate("return_date");
                loan.setReturnDate(r != null ? r.toLocalDate() : null);

                loan.setStatus(rs.getString("status"));

                // 🔴 Thêm
                loan.setFee(rs.getDouble("fee"));
                loan.setPaid(rs.getString("paid"));

                return loan;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // === UPDATE RETURN ===
    public boolean updateReturn(long loanId, LocalDate returnDate) {
        String sql = "UPDATE loan SET return_date=?, status='Returned' WHERE loan_id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(returnDate));
            stmt.setLong(2, loanId);

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // === UPDATE PAID ===
    public boolean markPaid(long loanId) {
        String sql = "UPDATE loan SET paid='Yes' WHERE loan_id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, loanId);
            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
