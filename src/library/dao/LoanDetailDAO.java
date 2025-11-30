package library.dao;

import library.database.DatabaseConnection;
import library.model.BookCopy;
import library.model.Loan;
import library.model.LoanDetail;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoanDetailDAO {

    public boolean createDetail(long loanId, long copyId) {
        String sql = "INSERT INTO loan_detail (loan_id, copy_id) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, loanId);
            stmt.setLong(2, copyId);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public LoanDetail getDetailByLoanId(long loanId) {
        String sql = "SELECT ld.id, ld.loan_id, ld.copy_id, bc.book_id, bc.status FROM loan_detail ld JOIN book_copy bc ON ld.copy_id = bc.copy_id WHERE ld.loan_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, loanId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                LoanDetail d = new LoanDetail();
                d.setId(rs.getLong("id"));
                Loan l = new Loan(); l.setId(rs.getLong("loan_id")); d.setLoan(l);
                BookCopy c = new BookCopy(); c.setCopyId(rs.getLong("copy_id")); c.setBookId(rs.getLong("book_id")); c.setStatus(rs.getString("status")); d.setCopy(c);
                return d;
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    public List<LoanDetail> getHistoryByMemberId(long memberId) {
        List<LoanDetail> list = new ArrayList<>();
        String sql = "SELECT ld.id AS detail_id, ld.copy_id, l.* FROM loan_detail ld JOIN loan l ON ld.loan_id = l.loan_id WHERE l.member_id = ? ORDER BY l.loan_date DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, memberId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Loan loan = new Loan();
                loan.setId(rs.getLong("loan_id"));
                loan.setLoanDate(rs.getTimestamp("loan_date").toLocalDateTime());
                Timestamp rDate = rs.getTimestamp("return_date");
                if (rDate != null) loan.setReturnDate(rDate.toLocalDateTime());
                loan.setStatus(rs.getString("status"));
                BookCopy copy = new BookCopy();
                copy.setCopyId(rs.getLong("copy_id"));
                LoanDetail d = new LoanDetail();
                d.setLoan(loan); d.setCopy(copy);
                list.add(d);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
}