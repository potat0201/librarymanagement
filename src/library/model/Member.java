package library.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Member extends Person {

    private long id;
    private UserAccount account;
    private String address;
    private LocalDate registrationDate;
    private String status;

    private List<Loan> loans = new ArrayList<>();
    private List<Payment> payments = new ArrayList<>();

    public Member() { super(); }

    public Member(long id, UserAccount account, String firstName, 
                  String lastName, String address, String phone,
                  LocalDate registrationDate, String status) {
        super(firstName, lastName, phone);
        this.id = id;
        this.account = account;
        this.address = address;
        this.registrationDate = registrationDate;
        this.status = status;
    }

    // Getter & Setter
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public UserAccount getAccount() { return account; }
    public void setAccount(UserAccount account) { this.account = account; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public LocalDate getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(LocalDate registrationDate) { this.registrationDate = registrationDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public List<Loan> getLoans() { return loans; }
    public void addLoan(Loan loan) {
        if (loan != null) {
            loans.add(loan);
            loan.setMember(this); // 2 chiều
        }
    }

    public void addPayment(Payment payment) {
        if (payment != null) {
            payments.add(payment);
            payment.setMember(this);
        }
    }

    @Override
    public String toString() {
        return "Member{id=" + id +
                ", name='" + getFullName() + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
