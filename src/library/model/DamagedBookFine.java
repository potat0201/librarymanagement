package library.model;

import java.math.BigDecimal;

public class DamagedBookFine implements FineCalculator {

    private BigDecimal damagePercentage; // phần trăm giá trị sách bị hỏng
    private BigDecimal bookBasePrice;

    public DamagedBookFine(BigDecimal damagePercentage, BigDecimal bookBasePrice) {
        this.damagePercentage = damagePercentage;
        this.bookBasePrice = bookBasePrice;
    }

    @Override
    public BigDecimal calculateFine(Loan loan) {
        return bookBasePrice.multiply(damagePercentage);
    }
}
