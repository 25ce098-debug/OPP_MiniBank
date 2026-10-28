/**
 * Account — represents a bank account with encapsulated balance.
 * Balance can only change through deposit() and withdraw(), never directly.
 * Demonstrates encapsulation, static counter, constructor chaining.
 */
public class Account {

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
     * Subtracts the given amount from the balance if sufficient funds exist.
     * @return true if withdrawal succeeded, false otherwise.
     */
    public boolean withdraw(long amount) {
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be positive.");
            return false;
        }
        if (amount > balance) {
            return false; // insufficient funds
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
}
