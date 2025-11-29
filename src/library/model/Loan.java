package library.model;

import java.time.LocalDate;

public class Loan {

    private long id;            // loan_id
    private long memberId;      // FK -> member
    private long staffId;       // FK -> staff
    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private String status;
    private int renewalCount;

    public Loan() {}

    public Loan(long id, long memberId, long staffId,
                LocalDate loanDate, LocalDate dueDate,
                LocalDate returnDate, String status, int renewalCount) {
        this.id = id;
        this.memberId = memberId;
        this.staffId = staffId;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.status = status;
        this.renewalCount = renewalCount;
    }

    // Getter & Setter

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getMemberId() { return memberId; }
    public void setMemberId(long memberId) { this.memberId = memberId; }

    public long getStaffId() { return staffId; }
    public void setStaffId(long staffId) { this.staffId = staffId; }

    public LocalDate getLoanDate() { return loanDate; }
    public void setLoanDate(LocalDate loanDate) { this.loanDate = loanDate; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getRenewalCount() { return renewalCount; }
    public void setRenewalCount(int renewalCount) { this.renewalCount = renewalCount; }

    @Override
    public String toString() {
        return "Loan{id=" + id +
                ", memberId=" + memberId +
                ", staffId=" + staffId +
                ", loanDate=" + loanDate +
                ", dueDate=" + dueDate +
                ", status=" + status +
                ", renewalCount=" + renewalCount +
                '}';
    }

    public void setStaff(Staff staff) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setStaff'");
    }

    public void setMember(Member member) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setMember'");
    }
}
