import java.util.Objects;

/**
 * Account — represents an abstract bank account with encapsulated balance.
 * Balance can only change through deposit() and withdraw(), never directly.
 * Demonstrates encapsulation, static counter, constructor chaining.
 * Practical 3: toString, equals/hashCode based on accountNumber.
 * Practical 5: abstract class with interestRate() and canWithdraw(amount).
 */
public abstract class Account {

    private static long accountCounter = 0;

    private final String accountNumber;
    private String ownerName;
    private long balance;
    private boolean active;

    /**
     * Creates an account with a given owner name and opening balance.
     * Account number is auto-generated (e.g., "AC0001").
     */
    public Account(String ownerName, long openingBalance) {
        accountCounter++;
        this.accountNumber = String.format("AC%04d", accountCounter);
        this.ownerName = ownerName;
        this.balance = openingBalance;
        this.active = true;
    }

    /**
     * Creates an account with zero opening balance.
     * Demonstrates constructor chaining with this().
     */
    public Account(String ownerName) {
        this(ownerName, 0);
    }

    /**
     * Protected constructor to allow controlled creation (e.g., testing equality).
     */
    protected Account(String accountNumber, String ownerName, long openingBalance) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = openingBalance;
        this.active = true;
    }

    /**
     * Abstract method returning annual interest rate percentage.
     */
    public abstract double interestRate();

    /**
     * Abstract method determining if a withdrawal amount is allowed by subtype rules.
     */
    public abstract boolean canWithdraw(long amount);

    /**
     * Adds the given amount to the balance.
     * Rejects negative or zero amounts.
     */
    public void deposit(long amount) {
        if (amount <= 0) {
            System.out.println("Deposit amount must be positive.");
            return;
        }
        balance += amount;
    }

    /**
     * Subtracts the given amount from the balance if allowed by subtype rules.
     * @return true if withdrawal succeeded, false otherwise.
     */
    public boolean withdraw(long amount) {
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be positive.");
            return false;
        }
        if (!canWithdraw(amount)) {
            return false; // insufficient funds or subtype rule restriction
        }
        balance -= amount;
        return true;
    }

    // --- Getters (no public setter for balance) ---

    public String getAccountNumber() { return accountNumber; }
    public String getOwnerName()     { return ownerName; }
    public long   getBalance()       { return balance; }
    public boolean isActive()        { return active; }

    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }
    public void setActive(boolean active)      { this.active = active; }

    // --- Practical 3: Object methods ---

    /**
     * Returns a readable string like "AC0001 | Riya | Balance: 4000 | Active".
     */
    public String toString() {
        return accountNumber + " | " + ownerName + " | Balance: " + balance
                + " | " + (active ? "Active" : "Inactive");
    }

    /**
     * Two accounts are equal when their accountNumber values are equal.
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account other)) return false;
        return Objects.equals(accountNumber, other.accountNumber);
    }

    public int hashCode() {
        return Objects.hash(accountNumber);
    }
}
