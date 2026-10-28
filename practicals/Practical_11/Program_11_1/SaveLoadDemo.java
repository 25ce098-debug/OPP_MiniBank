import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

class UserProfile implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String studentId;
    private final String name;
    private final transient String sessionPin;

    public UserProfile(String studentId, String name, String sessionPin) {
        this.studentId = studentId;
        this.name = name;
        this.sessionPin = sessionPin;
    }

    @Override
    public String toString() {
        return "UserProfile{id='" + studentId + "', name='" + name + "', transientSessionPin='" + sessionPin + "'}";
    }
}

public class SaveLoadDemo {
    public static void main(String[] args) throws Exception {
        String filename = "profiles.ser";
        UserProfile[] original = {
            new UserProfile("25CE098", "Bhavy Pan", "PIN-9876"),
            new UserProfile("25CE101", "Aarav Patel", "PIN-4321")
        };

        System.out.println("--- Saving Objects to " + filename + " ---");
        for (UserProfile p : original) {
            System.out.println("  Before Save: " + p);
        }

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(original);
        }

        System.out.println("\n--- Reloading Objects from " + filename + " ---");
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            UserProfile[] loaded = (UserProfile[]) in.readObject();
            for (UserProfile p : loaded) {
                System.out.println("  After Load : " + p);
            }
        }
    }
}
