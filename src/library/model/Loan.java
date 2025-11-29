package library.model;

import java.time.LocalDate;

public class Loan {
    private long id;
    private long memberId;
    private long staffId;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private String status;   // Borrowed / Returned / Overdue...

    // 🔴 Thêm mới
    private double fee;      // số tiền cần thu
    private String paid;     // Yes / No

    public Loan() {}

    public Loan(long id, long memberId, long staffId,
                LocalDate loanDate, LocalDate dueDate,
                LocalDate returnDate, String status,
                double fee, String paid) {
        this.id = id;
        this.memberId = memberId;
        this.staffId = staffId;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.status = status;
        this.fee = fee;
        this.paid = paid;
    }

    // === Getter – Setter ===

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

    public double getFee() { return fee; }
    public void setFee(double fee) { this.fee = fee; }

    public String getPaid() { return paid; }
    public void setPaid(String paid) { this.paid = paid; }

    @Override
    public String toString() {
        return "Loan{" +
                "id=" + id +
                ", memberId=" + memberId +
                ", staffId=" + staffId +
                ", loanDate=" + loanDate +
                ", dueDate=" + dueDate +
                ", returnDate=" + returnDate +
                ", status='" + status + '\'' +
                ", fee=" + fee +
                ", paid='" + paid + '\'' +
                '}';
    }
}
