/**
 * Customer — represents a bank customer with auto-generated ID.
 * Practical 2: encapsulation, static counter.
 * Practical 3: nested Address class, Cloneable.
 */
public class Customer implements Cloneable {

    private static long customerCounter = 100;

    private final String customerId;
    private String name;
    private String email;
    private String mobile;
    private Address address;

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

    // --- Getters ---

    public String getCustomerId() { return customerId; }
    public String getName()       { return name; }
    public String getEmail()      { return email; }
    public String getMobile()     { return mobile; }
    public Address getAddress()   { return address; }

    public void setName(String name)       { this.name = name; }
    public void setEmail(String email)     { this.email = email; }
    public void setMobile(String mobile)   { this.mobile = mobile; }
    public void setAddress(Address address) { this.address = address; }

    /**
     * Returns a shallow copy of this Customer.
     */
    public Customer clone() {
        try {
            return (Customer) super.clone();
        } catch (CloneNotSupportedException e) {
            // Should not happen since we implement Cloneable
            throw new AssertionError("Clone not supported", e);
        }
    }

    public String toString() {
        String s = customerId + " | " + name + " | " + email + " | " + mobile;
        if (address != null) {
            s += " | " + address;
        }
        return s;
    }

    // --- Public Static Nested Class ---

    /**
     * Address — a static nested class holding address details.
     * Static nested class: does not need an instance of Customer to exist.
     */
    public static class Address {
        private String line;
        private String city;
        private String pincode;

        public Address(String line, String city, String pincode) {
            this.line = line;
            this.city = city;
            this.pincode = pincode;
        }

        public String getLine()    { return line; }
        public String getCity()    { return city; }
        public String getPincode() { return pincode; }

        public String toString() {
            return line + ", " + city + " - " + pincode;
        }
    }
}

