package model;

import java.util.Objects;
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
    public void deposit(long amount) {
        if (amount <= 0) {
            System.out.println("Deposit amount must be positive.");
            return;
        }
        balance += amount;
    }

    @Override
    public boolean withdraw(long amount) {
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be positive.");
            return false;
        }
        if (!canWithdraw(amount)) {
            return false;
        }
        balance -= amount;
        return true;
    }

    // --- Getters ---

    public String getAccountNumber() { return accountNumber; }
    public String getOwnerName()     { return ownerName; }

    @Override
    public long getBalance()         { return balance; }

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
