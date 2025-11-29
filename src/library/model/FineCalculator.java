package library.model;

import java.math.BigDecimal;

public interface FineCalculator {
    BigDecimal calculateFine(Loan loan); 
}
