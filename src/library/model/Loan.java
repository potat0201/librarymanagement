package library.model;

import java.time.LocalDateTime;

public class Loan extends BaseEntity {
    private long memberId;
    private long staffId;
    private LocalDateTime loanDate;
    private LocalDateTime dueDate;
    private LocalDateTime returnDate;
    private String status;
    private double fee;
    private String paid;

    public Loan() {
        super();
    }


    public long getMemberId() { return memberId; }
    public void setMemberId(long memberId) { this.memberId = memberId; }

    public long getStaffId() { return staffId; }
    public void setStaffId(long staffId) { this.staffId = staffId; }

    public LocalDateTime getLoanDate() { return loanDate; }
    public void setLoanDate(LocalDateTime loanDate) { this.loanDate = loanDate; }

    public LocalDateTime getDueDate() { return dueDate; }
    public void setDueDate(LocalDateTime dueDate) { this.dueDate = dueDate; }

    public LocalDateTime getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDateTime returnDate) { this.returnDate = returnDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double getFee() { return fee; }
    public void setFee(double fee) { this.fee = fee; }

    public String getPaid() { return paid; }
    public void setPaid(String paid) { this.paid = paid; }

    @Override
    public String toString() {
        return "Loan ID: " + id + " - Status: " + status;
    }
}