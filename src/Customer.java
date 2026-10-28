/**
 * Customer — represents a bank customer with auto-generated ID.
 * Demonstrates encapsulation, static counter, and constructor usage.
 */
public class Customer {

    private static long customerCounter = 100;

    private final String customerId;
    private String name;
    private String email;
    private String mobile;

    /**
     * Creates a new Customer with an auto-generated ID like "CUST101".
     */
    public Customer(String name, String email, String mobile) {
        this.customerId = generateCustomerId();
        this.name = name;
        this.email = email;
        this.mobile = mobile;
    }

    private static String generateCustomerId() {
        customerCounter++;
        return "CUST" + customerCounter;
    }

    // --- Getters (no public setter for customerId) ---

    public String getCustomerId() { return customerId; }
    public String getName()       { return name; }
    public String getEmail()      { return email; }
    public String getMobile()     { return mobile; }

    public void setName(String name)     { this.name = name; }
    public void setEmail(String email)   { this.email = email; }
    public void setMobile(String mobile) { this.mobile = mobile; }
}
