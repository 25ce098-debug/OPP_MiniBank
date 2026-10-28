package service;

import model.Account;
import exception.BankException;
import exception.AccountNotFoundException;

/**
 * SafeTransferService provides deadlock-free transfers using global lock hierarchy ordering.
 * Semester 3 Object-Oriented Programming (CEUE203) - Practical 10
 */
public class SafeTransferService {

    /**
     * Executes safe transfer by always acquiring locks in strict lexicographical order of accountNumber.
     * This eliminates the Circular Wait condition (Coffman condition), guaranteeing deadlock freedom.
     */
    public static void safeTransfer(Account sourceAccount, Account destinationAccount, long amount) throws BankException {
        if (sourceAccount == null || destinationAccount == null) {
            throw new AccountNotFoundException("Transfer failed: account cannot be null.");
        }
        if (sourceAccount.equals(destinationAccount)) {
            throw new BankException("Transfer failed: cannot transfer to identical account.");
        }

        // Determine consistent global lock ordering by account number (smaller account number locked first)
        boolean sourceFirst = sourceAccount.getAccountNumber().compareTo(destinationAccount.getAccountNumber()) < 0;
        Account firstLock = sourceFirst ? sourceAccount : destinationAccount;
        Account secondLock = sourceFirst ? destinationAccount : sourceAccount;

        synchronized (firstLock) {
            synchronized (secondLock) {
                sourceAccount.withdraw(amount);
                destinationAccount.deposit(amount);
            }
        }
    }
}
