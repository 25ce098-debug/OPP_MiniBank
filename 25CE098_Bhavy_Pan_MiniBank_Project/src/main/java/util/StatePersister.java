package util;

import model.Account;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * StatePersister handles binary serialization and deserialization of account arrays.
 * Synchronizes Account ID counters to prevent collisions after loading state.
 * Semester 3 Object-Oriented Programming (CEUE203) - Practical 11
 */
public final class StatePersister {

    private StatePersister() {
        // Utility class
    }

    public static void save(Account[] accounts, Path file) throws IOException {
        if (file.getParent() != null) {
            Files.createDirectories(file.getParent());
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(file))) {
            oos.writeObject(accounts);
        }
    }

    public static Account[] load(Path file) throws IOException, ClassNotFoundException {
        if (!Files.exists(file)) {
            return new Account[0];
        }
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(file))) {
            Account[] accounts = (Account[]) ois.readObject();
            long maxId = 0;
            if (accounts != null) {
                for (Account acc : accounts) {
                    if (acc != null && acc.getAccountNumber() != null && acc.getAccountNumber().startsWith("AC")) {
                        try {
                            long idNum = Long.parseLong(acc.getAccountNumber().substring(2));
                            if (idNum > maxId) {
                                maxId = idNum;
                            }
                        } catch (NumberFormatException ignored) {}
                    }
                }
            }
            Account.synchronizeCounter(maxId);
            return accounts;
        }
    }
}
