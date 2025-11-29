package library.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Payment {

    private long id;
    private Member member;
    private Loan loan;
    private BigDecimal amount;
    private LocalDateTime paymentDate;
    private String reason;
    private String status;

    public Payment() {}

    public Payment(long id, Member member, Loan loan,
                   BigDecimal amount, LocalDateTime paymentDate,
                   String reason, String status) {
        this.id = id;
        this.member = member;
        this.loan = loan;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.reason = reason;
        this.status = status;
    }

    // Getter & Setter
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public Member getMember() { return member; }
    public void setMember(Member member) { this.member = member; }

    public Loan getLoan() { return loan; }
    public void setLoan(Loan loan) { this.loan = loan; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public LocalDateTime getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDateTime paymentDate) { this.paymentDate = paymentDate; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Payment{id=" + id +
                ", member=" + (member != null ? member.getFullName() : "null") +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                '}';
    }
}
