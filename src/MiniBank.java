import java.util.Scanner;

/**
 * MiniBank — the main console banking application.
 * Practical 1: Menu shell with enum, record, switch expression.
 * Practical 2: Customer and Account classes with encapsulation.
 * Practical 3: Object methods (toString, equals, hashCode), nested Address, clone.
 * Practical 4: Input validation, command parsing, statement formatting.
 * Practical 5: Inheritance and polymorphism (Savings, Current, FixedDeposit).
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
     * Practical 2 demonstration: creates accounts, performs deposits/withdrawals,
     * and prints results showing encapsulation in action.
     */
    private static void demonstrateAccountsAndCustomers() {
        System.out.println();
        System.out.println("--- Practical 2: Account & Customer Demo ---");

        // Create a customer
        Customer c1 = new Customer("Riya", "riya@example.com", "9876543210");
        System.out.println("Customer: " + c1.getName() + " (ID: " + c1.getCustomerId() + ")");

        // Create three accounts in an array
        Account[] accounts = {
            new SavingsAccount("Riya", 5000, 1000),
            new CurrentAccount("Arjun", 3000, 2000),
            new SavingsAccount("Meera")       // zero opening balance via chained constructor
        };

        // Deposit and withdraw
        accounts[0].deposit(2000);   // 5000 + 2000 = 7000
        boolean w1 = accounts[0].withdraw(3000); // 7000 - 3000 = 4000, true
        boolean w2 = accounts[0].withdraw(10000); // insufficient, false

        accounts[1].deposit(1500);   // 3000 + 1500 = 4500
        accounts[2].deposit(800);    // 0 + 800 = 800

        // Print results
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

        // toString
        Account a1 = new SavingsAccount("Riya", 4000, 1000);
        System.out.println("toString: " + a1);

        // equals — distinct object with same account number tests equals()
        Account a1Dup = new SavingsAccount(a1.getAccountNumber(), "Riya Dup", 4000, 1000);
        Account a2 = new CurrentAccount("Arjun", 2000, 3000);
        System.out.println("a1.equals(a1): " + a1.equals(a1));
        System.out.println("a1.equals(a1Dup) [same account number]: " + a1.equals(a1Dup));
        System.out.println("a1.equals(a2): " + a1.equals(a2));

        // instanceof check
        Object obj = a1;
        System.out.println("obj instanceof Account: " + (obj instanceof Account));

        // Customer with Address (nested class)
        Customer c = new Customer("Riya", "riya@example.com", "9876543210");
        Customer.Address addr = new Customer.Address("12 MG Road", "Anand", "388001");
        c.setAddress(addr);
        System.out.println("Customer: " + c);
        System.out.println("Address city: " + c.getAddress().getCity());

        // Clone
        Customer cloned = c.clone();
        System.out.println("Cloned customer: " + cloned);
        System.out.println("Original == Clone: " + (c == cloned));
        System.out.println("Original name equals clone: " + c.getName().equals(cloned.getName()));

        System.out.println("--- End of Practical 3 Demo ---");
    }

    /**
     * Practical 4 demonstration: validators, command parsing, statement formatting.
     */
    private static void demonstrateValidatorsAndParsing() {
        System.out.println();
        System.out.println("--- Practical 4: Validators & Parsing Demo ---");

        // Validator tests
        System.out.println("isValidMobile(\"9876543210\"): " + Validator.isValidMobile("9876543210"));
        System.out.println("isValidMobile(\"12345\"):      " + Validator.isValidMobile("12345"));
        System.out.println("isValidEmail(\"abc@xyz.com\"): " + Validator.isValidEmail("abc@xyz.com"));
        System.out.println("isValidEmail(\"notanemail\"):  " + Validator.isValidEmail("notanemail"));
        System.out.println("isValidPan(\"ABCDE1234F\"):   " + Validator.isValidPan("ABCDE1234F"));
        System.out.println("isValidPan(\"12345\"):         " + Validator.isValidPan("12345"));
        System.out.println("isValidIfsc(\"SBIN0001234\"): " + Validator.isValidIfsc("SBIN0001234"));
        System.out.println("isValidIfsc(\"INVALID\"):      " + Validator.isValidIfsc("INVALID"));

        // Command parsing
        Command cmd = CommandParser.parse("DEPOSIT AC0001 500");
        System.out.println("Parsed command: type=" + cmd.type()
                + ", accountNumber=" + cmd.accountNumber()
                + ", amount=" + cmd.amount());

        // Try parsing an invalid command
        try {
            CommandParser.parse("INVALID");
        } catch (IllegalArgumentException e) {
            System.out.println("Parse error (expected): " + e.getMessage());
        }

        // Statement formatting
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

        // Polymorphic array
        Account[] accounts = {
            new SavingsAccount("Riya", 5000, 1000),
            new CurrentAccount("Arjun", 2000, 5000),
            new FixedDepositAccount("Meera", 10000)
        };

        // Runtime polymorphism: interestRate() invoked dynamically per concrete subtype
        for (Account a : accounts) {
            System.out.println(a.getAccountNumber() + " | " + a.getOwnerName()
                    + " | Interest Rate: " + a.interestRate() + "%");

            // Pattern matching with instanceof
            if (a instanceof SavingsAccount sa) {
                System.out.println("   -> Subtype: SavingsAccount (Min Balance: Rs. " + sa.getMinBalance() + ")");
            } else if (a instanceof CurrentAccount ca) {
                System.out.println("   -> Subtype: CurrentAccount (Overdraft Limit: Rs. " + ca.getOverdraftLimit() + ")");
            } else if (a instanceof FixedDepositAccount) {
                System.out.println("   -> Subtype: FixedDepositAccount (Withdrawals Locked)");
            }
        }

        // Subtype-specific withdrawal rule verification
        System.out.println("\nTesting subtype withdrawal rules:");
        // Savings: balance 5000, min 1000 -> withdraw 4000 ok
        System.out.println("Savings withdraw 4000: " + accounts[0].withdraw(4000)
                + " (New Balance: " + accounts[0].getBalance() + ")");
        // Savings: balance 1000, min 1000 -> withdraw 500 fails (violates minBalance)
        System.out.println("Savings withdraw 500:  " + accounts[0].withdraw(500)
                + " (New Balance: " + accounts[0].getBalance() + ")");

        // Current: balance 2000, overdraft 5000 -> withdraw 5000 ok (balance drops to -3000)
        System.out.println("Current withdraw 5000: " + accounts[1].withdraw(5000)
                + " (New Balance: " + accounts[1].getBalance() + ")");
        // Current: balance -3000, overdraft 5000 -> withdraw 3000 fails (would exceed overdraft)
        System.out.println("Current withdraw 3000: " + accounts[1].withdraw(3000)
                + " (New Balance: " + accounts[1].getBalance() + ")");

        // Fixed Deposit: withdrawals are locked
        System.out.println("FD withdraw 1000:      " + accounts[2].withdraw(1000)
                + " (New Balance: " + accounts[2].getBalance() + ")");

        System.out.println("--- End of Practical 5 Demo ---");
    }
}
