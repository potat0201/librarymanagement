package library.model;

import java.math.BigDecimal;

public class LostBookFine implements FineCalculator {

    private BigDecimal lostPrice;  // tiền đền cố định

    public LostBookFine(BigDecimal lostPrice) {
        this.lostPrice = lostPrice;
    }

    @Override
    public BigDecimal calculateFine(Loan loan) {
        return lostPrice;
    }
}
