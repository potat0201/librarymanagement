package library.dao;

import library.database.DatabaseConnection;
import library.model.Loan;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LoanDAO {

    public long createLoan(Loan loan) {
        String sql = "INSERT INTO loan (member_id, staff_id, loan_date, due_date, status, fee, paid) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, loan.getMemberId());
            stmt.setLong(2, loan.getStaffId());
            stmt.setTimestamp(3, Timestamp.valueOf(loan.getLoanDate()));
            stmt.setTimestamp(4, Timestamp.valueOf(loan.getDueDate()));
            stmt.setString(5, loan.getStatus());
            stmt.setDouble(6, loan.getFee());
            stmt.setString(7, loan.getPaid());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) return rs.getLong(1);
        } catch (Exception e) { e.printStackTrace(); }
        return -1;
    }

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
                loan.setLoanDate(rs.getTimestamp("loan_date").toLocalDateTime());
                loan.setDueDate(rs.getTimestamp("due_date").toLocalDateTime());
                Timestamp r = rs.getTimestamp("return_date");
                if (r != null) loan.setReturnDate(r.toLocalDateTime());
                loan.setStatus(rs.getString("status"));
                loan.setFee(rs.getDouble("fee"));
                loan.setPaid(rs.getString("paid"));
                return loan;
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    public boolean updateReturn(long loanId, LocalDateTime returnDate) {
        String sql = "UPDATE loan SET return_date=?, status='Returned' WHERE loan_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setTimestamp(1, Timestamp.valueOf(returnDate));
            stmt.setLong(2, loanId);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public List<Object[]> getAllLoansInfo() {
        List<Object[]> list = new ArrayList<>();
        String sql = """
            SELECT l.loan_id, u.username, b.title, l.loan_date, l.return_date, l.status, l.fee, ld.copy_id
            FROM loan l
            JOIN user_account u ON l.member_id = u.user_id
            JOIN loan_detail ld ON l.loan_id = ld.loan_id
            JOIN book_copy bc ON ld.copy_id = bc.copy_id
            JOIN book b ON bc.book_id = b.book_id
            ORDER BY l.loan_date DESC
        """;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(new Object[] {
                    rs.getLong("loan_id"),
                    rs.getString("username"),
                    rs.getString("title"),
                    rs.getLong("copy_id"),
                    rs.getTimestamp("loan_date"),
                    rs.getTimestamp("return_date"),
                    rs.getString("status"),
                    rs.getDouble("fee")
                });
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
}