package library.service;

public class StandardFeePolicy implements IFeePolicy {
    @Override
    public double calculateFee() {
        return 5000.0; // phí cố định 5000đ
    }
}