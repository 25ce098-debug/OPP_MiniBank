import java.util.Scanner;

class DivideByZeroException extends Exception {
    public DivideByZeroException(String message) {
        super(message);
    }
}

public class GuardedCalculator {
    public static double compute(double a, double b, String op) throws DivideByZeroException {
        return switch (op) {
            case "+" -> a + b;
            case "-" -> a - b;
            case "*" -> a * b;
            case "/" -> {
                if (b == 0) {
                    throw new DivideByZeroException("Cannot divide " + a + " by zero.");
                }
                yield a / b;
            }
            default -> throw new IllegalArgumentException("Unsupported operator: " + op);
        };
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int attempt = 0;
        boolean success = false;

        while (!success && sc.hasNextLine()) {
            attempt++;
            System.out.print("Enter calculation (num1 operator num2): ");
            String line = sc.nextLine().trim();
            System.out.println(line);
            String[] parts = line.split("\\\s+");

            try {
                if (parts.length != 3) {
                    throw new NumberFormatException("Expected format: <num1> <op> <num2>");
                }
                double num1 = Double.parseDouble(parts[0]);
                String op = parts[1];
                double num2 = Double.parseDouble(parts[2]);

                double result = compute(num1, num2, op);
                System.out.println("Result: " + num1 + " " + op + " " + num2 + " = " + result);
                success = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid Number Error: Please enter valid numeric values (" + e.getMessage() + ").");
            } catch (DivideByZeroException e) {
                System.out.println("Math Error: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Operator Error: " + e.getMessage());
            } finally {
                System.out.println("[Finally Log] Attempt #" + attempt + " completed (Success=" + success + ").");
            }
        }
        sc.close();
    }
}
