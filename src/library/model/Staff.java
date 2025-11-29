package library.model;

import java.util.ArrayList;
import java.util.List;

public class Staff extends Person {

    private long id;
    private UserAccount account;

    private List<Loan> loans = new ArrayList<>();

    public Staff() { super(); }

    public Staff(long id, UserAccount account, String firstName,
                 String lastName, String phone) {
        super(firstName, lastName, phone);
        this.id = id;
        this.account = account;
    }

    // Getter & Setter
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public UserAccount getAccount() { return account; }
    public void setAccount(UserAccount account) { this.account = account; }

    public void addLoan(Loan loan) {
        if (loan != null) {
            loans.add(loan);
            loan.setStaff(this);
        }
    }

    @Override
    public String toString() {
        return "Staff{id=" + id + ", name='" + getFullName() + "'}";
    }
}
