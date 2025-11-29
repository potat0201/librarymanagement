package library.service;

import library.dao.BookCopyDAO;
import library.dao.LoanDAO;
import library.dao.LoanDetailDAO;
import library.model.Loan;
import library.model.LoanDetail;

import java.time.LocalDate;

public class LoanService {

    private LoanDAO loanDAO = new LoanDAO();
    private LoanDetailDAO detailDAO = new LoanDetailDAO();
    private BookCopyDAO copyDAO = new BookCopyDAO();

    // === BORROW BOOK ===
    public boolean borrowBook(long memberId, long copyId, long staffId) {

        Loan loan = new Loan();
        loan.setMemberId(memberId);
        loan.setStaffId(staffId);
        loan.setLoanDate(LocalDate.now());
        loan.setDueDate(LocalDate.now().plusDays(14));
        loan.setStatus("Borrowed");

        // 🔴 Thêm hệ thống phí mặc định
        loan.setFee(5000);
        loan.setPaid("No");

        long loanId = loanDAO.createLoan(loan);
        if (loanId == -1) return false;

        // Tạo Loan Detail
        LoanDetail detail = new LoanDetail();
        detail.setLoan(loan);
        detail.setCopy(copyDAO.getCopyById(copyId));

        boolean ok = detailDAO.createDetail(loanId, copyId);

        if (!ok) return false;

        return true;
    }
}
