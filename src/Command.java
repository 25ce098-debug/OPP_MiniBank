/**
 * Command — an immutable command parsed from a user input line.
 * Example: "DEPOSIT AC0001 500" becomes Command(DEPOSIT, "AC0001", 500).
 */
public record Command(TransactionType type, String accountNumber, long amount) {}

