import java.util.Scanner;

/**
 * MiniBank — the main console banking application.
 * Practical 1: Menu shell with enum, record, switch expression.
 * Practical 2: Customer and Account classes with encapsulation.
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
            new Account("Riya", 5000),
            new Account("Arjun", 3000),
            new Account("Meera")       // zero opening balance via chained constructor
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
}
