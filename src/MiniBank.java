import java.util.Scanner;
import model.*;
import util.*;
import service.*;
import exception.*;

// Practical 6: Static import to use Validator methods directly
import static util.Validator.*;

/**
 * MiniBank — the main console banking application.
 * Practical 1: Menu shell with enum, record, switch expression.
 * Practical 2: Customer and Account classes with encapsulation.
 * Practical 3: Object methods (toString, equals, hashCode), nested Address, clone.
 * Practical 4: Input validation, command parsing, statement formatting.
 * Practical 5: Inheritance and polymorphism (Savings, Current, FixedDeposit).
 * Practical 6: Interfaces (Transactable, InterestBearing, Premium),
 *              lambdas (WithdrawRule), package organization, static import.
 * Practical 7: Custom annotations (@Id, @Positive, @MaxLength) and reflection validation.
 * Practical 8: Custom exception hierarchy (BankException, InsufficientFundsException,
 *              InvalidAmountException, AccountNotFoundException), atomic transfers,
 *              try-catch-finally, try-with-resources.
 *
 * @author Bhavy Pan (25CE098)
 */
public class MiniBank {

    public static void main(String[] args) {
        // Bank information header
        BankInfo bank = new BankInfo("MiniBank", "CHARUSAT Branch");
        System.out.println("==========================================");
        System.out.println("  Welcome to " + bank.name());
        System.out.println("  Branch : " + bank.branch());
        System.out.println("==========================================");

        // --- Practical 2: Demonstrate Customer and Account ---
        demonstrateAccountsAndCustomers();

        // --- Practical 3: Demonstrate Object Methods ---
        demonstrateObjectMethods();

        // --- Practical 4: Demonstrate Validation and Parsing ---
        demonstrateValidatorsAndParsing();

        // --- Practical 5: Demonstrate Inheritance and Polymorphism ---
        demonstrateInheritanceAndPolymorphism();

        // --- Practical 6: Demonstrate Interfaces, Lambdas, and Packages ---
        demonstrateInterfacesAndLambdas();

        // --- Practical 7: Demonstrate Annotations and Reflection ---
        demonstrateAnnotationsAndReflection();

        // --- Practical 8: Demonstrate Exception Handling ---
        demonstrateExceptionHandling();

        // --- Menu Loop ---
        Scanner scanner = new Scanner(System.in);
        MenuOption[] options = MenuOption.values();
        boolean running = true;

        while (running) {
            System.out.println();
            System.out.println("--- Main Menu ---");
            for (int i = 0; i < options.length; i++) {
                System.out.println("  " + (i + 1) + ". " + options[i].label());
            }
            System.out.print("Enter your choice (1-" + options.length + "): ");

            // Read and validate input
            String input = scanner.nextLine().trim();
            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and " + options.length + ".");
                continue;
            }

            if (choice < 1 || choice > options.length) {
                System.out.println("Invalid choice. Please enter a number between 1 and " + options.length + ".");
                continue;
            }

            MenuOption selected = options[choice - 1];

            // Switch expression maps the selected option to an action
            String result = switch (selected) {
                case OPEN_ACCOUNT -> "Open Account — to be implemented in a later lab.";
                case DEPOSIT      -> "Deposit — to be implemented in a later lab.";
                case WITHDRAW     -> "Withdraw — to be implemented in a later lab.";
                case TRANSFER     -> "Transfer — to be implemented in a later lab.";
                case EXIT         -> null; // signals exit
            };

            if (result == null) {
                running = false;
            } else {
                System.out.println(result);
            }
        }

        System.out.println();
        System.out.println("Thank you for using " + bank.name() + ". Goodbye!");
        scanner.close();
    }

    /**
     * Practical 2 demonstration: creates accounts, performs deposits/withdrawals.
     */
    private static void demonstrateAccountsAndCustomers() {
        System.out.println();
        System.out.println("--- Practical 2: Account & Customer Demo ---");

        Customer c1 = new Customer("Riya", "riya@example.com", "9876543210");
        System.out.println("Customer: " + c1.getName() + " (ID: " + c1.getCustomerId() + ")");

        Account[] accounts = {
            new SavingsAccount("Riya", 5000, 1000),
            new CurrentAccount("Arjun", 3000, 2000),
            new SavingsAccount("Meera")
        };

        boolean w1 = false;
        boolean w2 = false;
        try {
            accounts[0].deposit(2000);
            w1 = accounts[0].withdraw(3000);
            w2 = accounts[0].withdraw(10000);
        } catch (BankException e) {
            w2 = false;
        }

        try {
            accounts[1].deposit(1500);
            accounts[2].deposit(800);
        } catch (BankException ignored) {}

        System.out.println();
        for (Account a : accounts) {
            System.out.println(a.getAccountNumber() + " | " + a.getOwnerName()
                    + " | Balance: " + a.getBalance());
        }
        System.out.println("Withdraw 3000 from " + accounts[0].getAccountNumber() + ": " + w1);
        System.out.println("Withdraw 10000 from " + accounts[0].getAccountNumber() + ": " + w2);
        System.out.println("--- End of Practical 2 Demo ---");
    }

    /**
     * Practical 3 demonstration: toString, equals/hashCode, nested Address, clone.
     */
    private static void demonstrateObjectMethods() {
        System.out.println();
        System.out.println("--- Practical 3: Object Methods Demo ---");

        Account a1 = new SavingsAccount("Riya", 4000, 1000);
        System.out.println("toString: " + a1);

        Account a1Dup = new SavingsAccount(a1.getAccountNumber(), "Riya Dup", 4000, 1000);
        Account a2 = new CurrentAccount("Arjun", 2000, 3000);
        System.out.println("a1.equals(a1): " + a1.equals(a1));
        System.out.println("a1.equals(a1Dup) [same account number]: " + a1.equals(a1Dup));
        System.out.println("a1.equals(a2): " + a1.equals(a2));

        Object obj = a1;
        System.out.println("obj instanceof Account: " + (obj instanceof Account));

        Customer c = new Customer("Riya", "riya@example.com", "9876543210");
        Customer.Address addr = new Customer.Address("12 MG Road", "Anand", "388001");
        c.setAddress(addr);
        System.out.println("Customer: " + c);
        System.out.println("Address city: " + c.getAddress().getCity());

        Customer cloned = c.clone();
        System.out.println("Cloned customer: " + cloned);
        System.out.println("Original == Clone: " + (c == cloned));
        System.out.println("Original name equals clone: " + c.getName().equals(cloned.getName()));

        System.out.println("--- End of Practical 3 Demo ---");
    }

    /**
     * Practical 4 demonstration: static import validator, command parsing, statement formatting.
     */
    private static void demonstrateValidatorsAndParsing() {
        System.out.println();
        System.out.println("--- Practical 4: Validators & Parsing Demo ---");

        System.out.println("isValidMobile(\"9876543210\"): " + isValidMobile("9876543210"));
        System.out.println("isValidMobile(\"12345\"):      " + isValidMobile("12345"));
        System.out.println("isValidEmail(\"abc@xyz.com\"): " + isValidEmail("abc@xyz.com"));
        System.out.println("isValidEmail(\"notanemail\"):  " + isValidEmail("notanemail"));
        System.out.println("isValidPan(\"ABCDE1234F\"):   " + isValidPan("ABCDE1234F"));
        System.out.println("isValidPan(\"12345\"):         " + isValidPan("12345"));
        System.out.println("isValidIfsc(\"SBIN0001234\"): " + isValidIfsc("SBIN0001234"));
        System.out.println("isValidIfsc(\"INVALID\"):      " + isValidIfsc("INVALID"));

        Command cmd = CommandParser.parse("DEPOSIT AC0001 500");
        System.out.println("Parsed command: type=" + cmd.type()
                + ", accountNumber=" + cmd.accountNumber()
                + ", amount=" + cmd.amount());

        try {
            CommandParser.parse("INVALID");
        } catch (IllegalArgumentException e) {
            System.out.println("Parse error (expected): " + e.getMessage());
        }

        Account acc = new SavingsAccount("Riya", 7000, 1000);
        System.out.println();
        System.out.println(StatementFormatter.buildStatement(acc));

        System.out.println("--- End of Practical 4 Demo ---");
    }

    /**
     * Practical 5 demonstration: inheritance, runtime polymorphism, pattern matching instanceof.
     */
    private static void demonstrateInheritanceAndPolymorphism() {
        System.out.println();
        System.out.println("--- Practical 5: Inheritance & Polymorphism Demo ---");

        Account[] accounts = {
            new SavingsAccount("Riya", 5000, 1000),
            new CurrentAccount("Arjun", 2000, 5000),
            new FixedDepositAccount("Meera", 10000)
        };

        for (Account a : accounts) {
            System.out.println(a.getAccountNumber() + " | " + a.getOwnerName()
                    + " | Interest Rate: " + a.interestRate() + "%");

            if (a instanceof SavingsAccount sa) {
                System.out.println("   -> Subtype: SavingsAccount (Min Balance: Rs. " + sa.getMinBalance() + ")");
            } else if (a instanceof CurrentAccount ca) {
                System.out.println("   -> Subtype: CurrentAccount (Overdraft Limit: Rs. " + ca.getOverdraftLimit() + ")");
            } else if (a instanceof FixedDepositAccount) {
                System.out.println("   -> Subtype: FixedDepositAccount (Withdrawals Locked)");
            }
        }

        System.out.println("\nTesting subtype withdrawal rules:");
        try {
            System.out.println("Savings withdraw 4000: " + accounts[0].withdraw(4000)
                    + " (New Balance: " + accounts[0].getBalance() + ")");
        } catch (BankException e) {
            System.out.println("Savings withdraw 4000 caught: " + e.getMessage());
        }

        try {
            System.out.println("Savings withdraw 500:  " + accounts[0].withdraw(500)
                    + " (New Balance: " + accounts[0].getBalance() + ")");
        } catch (BankException e) {
            System.out.println("Savings withdraw 500 caught: " + e.getMessage());
        }

        try {
            System.out.println("Current withdraw 5000: " + accounts[1].withdraw(5000)
                    + " (New Balance: " + accounts[1].getBalance() + ")");
        } catch (BankException e) {
            System.out.println("Current withdraw 5000 caught: " + e.getMessage());
        }

        try {
            System.out.println("Current withdraw 3000: " + accounts[1].withdraw(3000)
                    + " (New Balance: " + accounts[1].getBalance() + ")");
        } catch (BankException e) {
            System.out.println("Current withdraw 3000 caught: " + e.getMessage());
        }

        try {
            System.out.println("FD withdraw 1000:      " + accounts[2].withdraw(1000)
                    + " (New Balance: " + accounts[2].getBalance() + ")");
        } catch (BankException e) {
            System.out.println("FD withdraw 1000 caught: " + e.getMessage());
        }

        System.out.println("--- End of Practical 5 Demo ---");
    }

    /**
     * Practical 6 demonstration: interfaces, default methods, anonymous class vs lambda, marker interface.
     */
    private static void demonstrateInterfacesAndLambdas() {
        System.out.println();
        System.out.println("--- Practical 6: Interfaces, Lambdas & Packages Demo ---");

        SavingsAccount sa = new SavingsAccount("Riya", 50000, 1000);
        CurrentAccount ca = new CurrentAccount("Arjun", 100000, 20000);

        System.out.println(sa.getOwnerName() + " (Savings @ " + sa.interestRate() + "%) Yearly Interest: Rs. "
                + sa.yearlyInterest() + " | Monthly: Rs. " + sa.monthlyInterest());

        WithdrawRule anonRule = new WithdrawRule() {
            @Override
            public boolean allow(Account account, long amount) {
                return amount <= 25000;
            }
        };

        WithdrawRule lambdaRule = (account, amount) -> amount <= 25000;

        System.out.println("AnonRule allow(20000):   " + anonRule.allow(sa, 20000));
        System.out.println("AnonRule allow(30000):   " + anonRule.allow(sa, 30000));
        System.out.println("LambdaRule allow(20000): " + lambdaRule.allow(sa, 20000));
        System.out.println("LambdaRule allow(30000): " + lambdaRule.allow(sa, 30000));

        System.out.println("Is SavingsAccount Premium? " + (sa instanceof Premium));
        System.out.println("Is CurrentAccount Premium? " + (ca instanceof Premium));

        AccountService.printAccountSummary(sa);

        System.out.println("--- End of Practical 6 Demo ---");
    }

    /**
     * Practical 7 demonstration: custom annotations (@Id, @Positive, @MaxLength) and reflection validation.
     */
    private static void demonstrateAnnotationsAndReflection() {
        System.out.println();
        System.out.println("--- Practical 7: Annotations & Reflection Demo ---");

        Account invalidAccount = new SavingsAccount("Riya", -100, 1000);
        String[] invalidErrors = AnnotationValidator.validate(invalidAccount);
        System.out.println("Validating account with balance -100:");
        for (String err : invalidErrors) {
            System.out.println("  Validation Error: " + err);
        }

        Account validAccount = new SavingsAccount("Riya", 5000, 1000);
        String[] validErrors = AnnotationValidator.validate(validAccount);
        System.out.println("Validating account with balance 5000: Errors count = " + validErrors.length);

        Account longNameAccount = new SavingsAccount("VeryLongOwnerNameExceedingTwentyCharacters", 5000, 1000);
        String[] maxLengthErrors = AnnotationValidator.validate(longNameAccount);
        System.out.println("Validating account with long owner name (>20 chars):");
        for (String err : maxLengthErrors) {
            System.out.println("  Validation Error: " + err);
        }

        System.out.println("--- End of Practical 7 Demo ---");
    }

    /**
     * Practical 8 demonstration: custom banking exceptions, shortfall, atomic transfer,
     * try-catch-finally, try-with-resources.
     */
    private static void demonstrateExceptionHandling() {
        System.out.println();
        System.out.println("--- Practical 8: Exception Handling Demo (Milestone 1) ---");

        Account acc = new SavingsAccount("Riya", 1000, 0);

        // 1. Withdrawal exceeding balance throws InsufficientFundsException with shortfall
        try {
            System.out.println("Attempting withdraw(5000) from balance " + acc.getBalance() + "...");
            acc.withdraw(5000);
        } catch (InsufficientFundsException e) {
            System.out.println("Withdrawal failed: short by " + e.getShortfall() + " (Balance: " + acc.getBalance() + ")");
        } catch (BankException e) {
            System.out.println("Generic bank failure: " + e.getMessage());
        }

        // 2. Non-positive amount throws InvalidAmountException
        try {
            System.out.println("Attempting deposit(-200)...");
            acc.deposit(-200);
        } catch (InvalidAmountException e) {
            System.out.println("Caught InvalidAmountException: " + e.getMessage());
        }

        // 3. Safe inter-account transfer with try-catch-finally
        Account source = new SavingsAccount("Riya", 5000, 0);
        Account target = new SavingsAccount("Arjun", 2000, 0);
        System.out.println("\nInitial: Source Rs. " + source.getBalance() + ", Target Rs. " + target.getBalance());

        try {
            System.out.println("Executing transfer(1500) from Source to Target...");
            source.transfer(target, 1500);
            System.out.println("Transfer successful!");
        } catch (BankException e) {
            System.out.println("Transfer failed: " + e.getMessage());
        } finally {
            System.out.println("Finally block executed. Current Balances: Source="
                    + source.getBalance() + ", Target=" + target.getBalance());
        }

        // 4. Failed transfer keeps balances intact (atomic integrity)
        try {
            System.out.println("\nExecuting transfer(20000) exceeding Source balance...");
            source.transfer(target, 20000);
        } catch (InsufficientFundsException e) {
            System.out.println("Transfer rejected: short by " + e.getShortfall());
        } catch (BankException e) {
            System.out.println("Transfer error: " + e.getMessage());
        } finally {
            System.out.println("Integrity Check: Source=" + source.getBalance() + ", Target=" + target.getBalance());
        }

        // 5. Try-with-resources with AutoCloseable resource
        System.out.println("\nDemonstrating try-with-resources:");
        try (TransactionSession session = new TransactionSession("TX-8801")) {
            System.out.println("Operating inside try-with-resources block...");
            source.withdraw(500);
            System.out.println("Withdrawn Rs. 500 in session " + session.getSessionId());
        } catch (BankException e) {
            System.out.println("Session error: " + e.getMessage());
        }

        System.out.println("--- End of Practical 8 Demo ---");
    }
}
