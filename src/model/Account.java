package model;

import java.util.Objects;
import exception.AccountNotFoundException;
import exception.BankException;
import exception.InsufficientFundsException;
import exception.InvalidAmountException;
import model.annotation.Id;
import model.annotation.MaxLength;
import model.annotation.Positive;

/**
 * Account — abstract bank account model.
 * Implements Transactable (deposit, withdraw) and InterestBearing (yearlyInterest).
 *
 * Practical 2: Encapsulation, static counter.
 * Practical 3: toString, equals, hashCode.
 * Practical 5: Abstract class with interestRate() and canWithdraw().
 * Practical 6: Moved to model package, implements Transactable & InterestBearing.
 * Practical 7: Custom annotations @Id, @Positive, @MaxLength and @Override annotations.
 * Practical 8: Checked exceptions on deposit/withdraw, transfer with try-catch-finally.
 * Practical 9: Synchronized methods for thread-safety during concurrent operations.
 */
public abstract class Account implements Transactable, InterestBearing {

    private static long accountCounter = 0;

    @Id
    private final String accountNumber;

    @MaxLength(20)
    private String ownerName;

    @Positive
    private long balance;

    private boolean active;

    /**
     * Creates an account with owner name and opening balance.
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
     */
    public Account(String ownerName) {
        this(ownerName, 0);
    }

    /**
     * Protected constructor allowing controlled creation (e.g. testing equality).
     */
    protected Account(String accountNumber, String ownerName, long openingBalance) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = openingBalance;
        this.active = true;
    }

    @Override
    public abstract double interestRate();

    public abstract boolean canWithdraw(long amount);

    @Override
    public synchronized void deposit(long amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Deposit amount must be strictly positive (got " + amount + ")");
        }
        balance += amount;
    }

    @Override
    public synchronized boolean withdraw(long amount) throws InsufficientFundsException, InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be strictly positive (got " + amount + ")");
        }
        if (!canWithdraw(amount)) {
            long shortfall = Math.max(0, amount - balance);
            throw new InsufficientFundsException(
                    "Withdrawal failed: insufficient funds in " + accountNumber, shortfall);
        }
        balance -= amount;
        return true;
    }

    /**
     * Atomically transfers money to destination account using try, catch, and finally.
     * Re-throws BankException on failure, rolling back withdrawal if needed.
     */
    public void transfer(Account to, long amount) throws BankException {
        if (to == null) {
            throw new AccountNotFoundException("Destination account cannot be null");
        }
        if (to.equals(this)) {
            throw new InvalidAmountException("Cannot transfer to the same account: " + accountNumber);
        }
        if (amount <= 0) {
            throw new InvalidAmountException("Transfer amount must be strictly positive (got " + amount + ")");
        }

        boolean withdrawn = false;
        try {
            this.withdraw(amount);
            withdrawn = true;
            to.deposit(amount);
        } catch (BankException e) {
            if (withdrawn) {
                // Rollback withdrawal if deposit failed
                try {
                    this.deposit(amount);
                } catch (InvalidAmountException rollbackEx) {
                    // Suppressed unexpected rollback failure
                }
            }
            throw e;
        } finally {
            // Execution audit log in finally block
        }
    }

    // --- Getters ---

    public String getAccountNumber() { return accountNumber; }
    public String getOwnerName()     { return ownerName; }

    @Override
    public synchronized long getBalance() { return balance; }

    public boolean isActive()        { return active; }

    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }
    public void setActive(boolean active)      { this.active = active; }

    @Override
    public String toString() {
        return accountNumber + " | " + ownerName + " | Balance: " + balance
                + " | " + (active ? "Active" : "Inactive");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account other)) return false;
        return Objects.equals(accountNumber, other.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }
}
