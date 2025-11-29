package library.model;

public class LoanDetail {

    private long id;
    private Loan loan;        // tham chiếu tới Loan
    private BookCopy copy;    // tham chiếu tới BookCopy

    public LoanDetail() {
    }

    public LoanDetail(long id, Loan loan, BookCopy copy) {
        this.id = id;
        this.loan = loan;
        this.copy = copy;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public BookCopy getCopy() {
        return copy;
    }

    public void setCopy(BookCopy copy) {
        this.copy = copy;
    }

    @Override
    public String toString() {
        return "LoanDetail{id=" + id +
                ", loanId=" + (loan != null ? loan.getId() : null) +
                ", copyId=" + (copy != null ? copy.getCopyId() : null) +
                '}';
    }
}
