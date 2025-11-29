package library.dao;

import library.database.DatabaseConnection;
import library.model.BookCopy;
import library.model.Loan;
import library.model.LoanDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LoanDetailDAO {

    // Tạo loan_detail: dùng trong LoanService.borrowBook(...)
    public boolean createDetail(long loanId, long copyId) {
        String sql = "INSERT INTO loan_detail (loan_id, copy_id) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, loanId);
            stmt.setLong(2, copyId);

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy LoanDetail theo loanId – dùng cho ReturnGUI
    public LoanDetail getDetailByLoanId(long loanId) {

        String sql =
                "SELECT ld.id, ld.loan_id, ld.copy_id, bc.book_id, bc.status " +
                "FROM loan_detail ld " +
                "JOIN book_copy bc ON ld.copy_id = bc.copy_id " +
                "WHERE ld.loan_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, loanId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                LoanDetail detail = new LoanDetail();
                detail.setId(rs.getLong("id"));

                // Loan (chỉ set id, không cần load hết)
                Loan loan = new Loan();
                loan.setId(rs.getLong("loan_id"));
                detail.setLoan(loan);

                // BookCopy (set đầy đủ id, bookId, status)
                BookCopy copy = new BookCopy();
                copy.setCopyId(rs.getLong("copy_id"));
                copy.setBookId(rs.getLong("book_id"));
                copy.setStatus(rs.getString("status"));
                detail.setCopy(copy);

                return detail;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<LoanDetail> getHistoryByMemberId(long memberId) {
    List<LoanDetail> list = new ArrayList<>();

    String sql = """
        SELECT ld.id AS detail_id, ld.copy_id, l.*
        FROM loan_detail ld
        JOIN loan l ON ld.loan_id = l.loan_id
        WHERE l.member_id = ?
        ORDER BY l.loan_date DESC
    """;

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setLong(1, memberId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Loan loan = new Loan();
            loan.setId(rs.getLong("loan_id"));
            loan.setLoanDate(rs.getDate("loan_date").toLocalDate());
            loan.setReturnDate(rs.getDate("return_date") != null ? rs.getDate("return_date").toLocalDate() : null);
            loan.setStatus(rs.getString("status"));

            BookCopy copy = new BookCopy();
            copy.setCopyId(rs.getLong("copy_id"));

            LoanDetail d = new LoanDetail();
            d.setLoan(loan);
            d.setCopy(copy);

            list.add(d);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}

}
