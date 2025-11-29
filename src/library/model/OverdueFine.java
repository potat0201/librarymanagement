package library.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class OverdueFine implements FineCalculator {

    private BigDecimal dailyRate;

    public OverdueFine(BigDecimal dailyRate) {
        this.dailyRate = dailyRate;
    }

    @Override
    public BigDecimal calculateFine(Loan loan) {

        LocalDate due = loan.getDueDate();
        LocalDate today = loan.getReturnDate() == null
                ? LocalDate.now()
                : loan.getReturnDate();

        if (!today.isAfter(due)) {
            return BigDecimal.ZERO;
        }

        long daysLate = ChronoUnit.DAYS.between(due, today);

        return dailyRate.multiply(BigDecimal.valueOf(daysLate));
    }
}
