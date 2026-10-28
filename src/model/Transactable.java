package model;

/**
 * Transactable — defines core transaction capabilities for accounts.
 */
public interface Transactable {
    void deposit(long amount);
    boolean withdraw(long amount);
}
