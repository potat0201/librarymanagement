package library.service;

import library.dao.BookCopyDAO;
import library.dao.LoanDAO;
import library.dao.LoanDetailDAO;
import library.model.Loan;
import library.model.LoanDetail;
import java.time.LocalDateTime;
import java.util.List;

public class LoanService {

    private LoanDAO loanDAO = new LoanDAO();
    private LoanDetailDAO detailDAO = new LoanDetailDAO();
    private BookCopyDAO copyDAO = new BookCopyDAO();
    
    // Áp dụng Đa hình: Khai báo Interface, khởi tạo Class thực thi
    private IFeePolicy feePolicy = new StandardFeePolicy(); 

    public boolean borrowBook(long memberId, long copyId, long staffId) {
        Loan loan = new Loan();
        loan.setMemberId(memberId);
        loan.setStaffId(staffId);
        loan.setLoanDate(LocalDateTime.now());
        loan.setDueDate(LocalDateTime.now().plusDays(14)); 
        loan.setStatus("Borrowed");
        
        // Gọi phương thức tính phí qua Interface (Đa hình)
        loan.setFee(feePolicy.calculateFee());
        
        loan.setPaid("No");

        long loanId = loanDAO.createLoan(loan);
        if (loanId == -1) return false;

        boolean okDetail = detailDAO.createDetail(loanId, copyId);
        boolean okCopy = copyDAO.updateStatus(copyId, "Borrowed");

        return okDetail && okCopy;
    }

    public boolean returnBook(long loanId, long copyId) {
        boolean updateLoan = loanDAO.updateReturn(loanId, LocalDateTime.now());
        boolean updateCopy = copyDAO.updateStatus(copyId, "Available");
        return updateLoan && updateCopy;
    }

    public List<LoanDetail> getLoanHistory(long memberId) {
        return detailDAO.getHistoryByMemberId(memberId);
    }
}