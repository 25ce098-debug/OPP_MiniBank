/**
 * FixedDepositAccount — account type with locked funds and 7.0% interest.
 * Extends Account. Withdrawals are locked (canWithdraw returns false).
 */
public class FixedDepositAccount extends Account {

    public FixedDepositAccount(String ownerName, long openingBalance) {
        super(ownerName, openingBalance);
    }

    public FixedDepositAccount(String ownerName) {
        this(ownerName, 0);
    }

    public FixedDepositAccount(String accountNumber, String ownerName, long openingBalance) {
        super(accountNumber, ownerName, openingBalance);
    }

    public double interestRate() {
        return 7.0;
    }

    public boolean canWithdraw(long amount) {
        return false; // locked deposit
    }

    public String toString() {
        return super.toString() + " | FixedDeposit (Locked, Rate: " + interestRate() + "%)";
    }
}
