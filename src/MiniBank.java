import java.util.Scanner;

/**
 * MiniBank — the main console banking application.
 * Practical 1: Sets up the menu shell; later practicals attach real features.
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
}
