package library.dao;

import library.database.DatabaseConnection;
import library.model.Loan;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LoanDAO {

    // Lấy loan theo ID
    public Loan getLoanById(long loanId) {
        String sql = """
            SELECT loan_id, member_id, staff_id, loan_date,
                   due_date, return_date, status, renewal_count
            FROM loan
            WHERE loan_id = ?
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, loanId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Loan(
                        rs.getLong("loan_id"),
                        rs.getLong("member_id"),
                        rs.getLong("staff_id"),
                        rs.getDate("loan_date").toLocalDate(),
                        rs.getDate("due_date").toLocalDate(),
                        rs.getDate("return_date") != null ? rs.getDate("return_date").toLocalDate() : null,
                        rs.getString("status"),
                        rs.getInt("renewal_count")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    // Lấy danh sách loan của 1 member
    public List<Loan> getLoansByMember(long memberId) {
        List<Loan> list = new ArrayList<>();

        String sql = """
            SELECT loan_id, member_id, staff_id, loan_date,
                   due_date, return_date, status, renewal_count
            FROM loan
            WHERE member_id = ?
            ORDER BY loan_date DESC
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, memberId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new Loan(
                        rs.getLong("loan_id"),
                        rs.getLong("member_id"),
                        rs.getLong("staff_id"),
                        rs.getDate("loan_date").toLocalDate(),
                        rs.getDate("due_date").toLocalDate(),
                        rs.getDate("return_date") != null ? rs.getDate("return_date").toLocalDate() : null,
                        rs.getString("status"),
                        rs.getInt("renewal_count")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    // Tạo loan mới (khi mượn sách)
    public long createLoan(Loan loan) {

        String sql = """
            INSERT INTO loan(member_id, staff_id, loan_date,
                             due_date, status, renewal_count)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setLong(1, loan.getMemberId());
            stmt.setLong(2, loan.getStaffId());
            stmt.setDate(3, Date.valueOf(loan.getLoanDate()));
            stmt.setDate(4, Date.valueOf(loan.getDueDate()));
            stmt.setString(5, loan.getStatus());
            stmt.setInt(6, loan.getRenewalCount());

            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                return keys.getLong(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    

    // Update return_date & status (trả sách)
    public boolean returnLoan(long loanId) {

        String sql = """
            UPDATE loan
            SET return_date = ?, status = 'Returned'
            WHERE loan_id = ?
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(LocalDate.now()));
            stmt.setLong(2, loanId);

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    // Update trạng thái loan (Borrowed, Returned, Overdue...)
    public boolean updateStatus(long loanId, String status) {

        String sql = "UPDATE loan SET status = ? WHERE loan_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setLong(2, loanId);

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateReturn(long loanId, LocalDate returnDate) {
    String sql = "UPDATE loan SET return_date=?, status='Returned' WHERE loan_id=?";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setDate(1, java.sql.Date.valueOf(returnDate));
        stmt.setLong(2, loanId);

        return stmt.executeUpdate() > 0;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}

}
