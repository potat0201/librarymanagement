package library.service;

import library.dao.BookCopyDAO;
import library.dao.LoanDAO;
import library.dao.LoanDetailDAO;
import library.model.BookCopy;
import library.model.Loan;

import java.time.LocalDate;

public class LoanService {

    private LoanDAO loanDAO = new LoanDAO();
    private LoanDetailDAO loanDetailDAO = new LoanDetailDAO();
    private BookCopyDAO copyDAO = new BookCopyDAO();

    /**
     * BORROW BOOK
     */
    public boolean borrowBook(long memberId, long copyId, long staffId) {

    BookCopy copy = copyDAO.getCopyById(copyId);
    if (copy == null || !copy.isAvailable()) {
        System.out.println("❌ Copy không khả dụng!");
        return false;
    }

    Loan loan = new Loan();
    loan.setMemberId(memberId);
    loan.setStaffId(staffId);
    loan.setLoanDate(LocalDate.now());
    loan.setDueDate(LocalDate.now().plusDays(14));
    loan.setStatus("Borrowed");
    loan.setRenewalCount(0);

    long loanId = loanDAO.createLoan(loan);
    if (loanId == -1) return false;

    boolean ok = loanDetailDAO.createDetail(loanId, copyId);
    if (!ok) return false;

    copyDAO.updateStatus(copyId, "Borrowed");

    return true;
}



    /**
     * RETURN BOOK
     */
    public boolean returnBook(long loanId, long copyId) {

        // 1. Update loan: cập nhật return_date + set status = Returned
        boolean ok1 = loanDAO.updateReturn(loanId, LocalDate.now());
        if (!ok1) {
            System.out.println("❌ Không update loan khi trả sách!");
            return false;
        }

        // 2. Update copy back to InStock
        boolean ok2 = copyDAO.updateStatus(copyId, "InStock");
        if (!ok2) {
            System.out.println("❌ Không cập nhật trạng thái copy!");
            return false;
        }

        System.out.println("✅ Trả sách thành công!");
        return true;
    }
}
