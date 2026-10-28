import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

class BankVault {
    private final int id;
    private final String name;
    private int balance;

    public BankVault(int id, String name, int balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getBalance() { return balance; }
    public void debit(int amt) { balance -= amt; }
    public void credit(int amt) { balance += amt; }
}

public class DeadlockDemo {
    public static void transferSafe(BankVault from, BankVault to, int amount) {
        BankVault firstLock = (from.getId() < to.getId()) ? from : to;
        BankVault secondLock = (from.getId() < to.getId()) ? to : from;

        synchronized (firstLock) {
            synchronized (secondLock) {
                from.debit(amount);
                to.credit(amount);
                System.out.println("  [Safe Transfer] Moved " + amount + " from " + from.getName() + " to " + to.getName());
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        BankVault vaultA = new BankVault(1, "Vault-A", 1000);
        BankVault vaultB = new BankVault(2, "Vault-B", 1000);

        System.out.println("=== Part 1: Reproducing Deadlock (Opposite Lock Order) ===");
        Thread t1 = new Thread(() -> {
            synchronized (vaultA) {
                System.out.println("  Thread-1 locked " + vaultA.getName() + ", waiting for " + vaultB.getName() + "...");
                try { Thread.sleep(50); } catch (InterruptedException ignored) {}
                synchronized (vaultB) {
                    vaultA.debit(100);
                    vaultB.credit(100);
                }
            }
        }, "Thread-1");

        Thread t2 = new Thread(() -> {
            synchronized (vaultB) {
                System.out.println("  Thread-2 locked " + vaultB.getName() + ", waiting for " + vaultA.getName() + "...");
                try { Thread.sleep(50); } catch (InterruptedException ignored) {}
                synchronized (vaultA) {
                    vaultB.debit(200);
                    vaultA.credit(200);
                }
            }
        }, "Thread-2");

        t1.setDaemon(true);
        t2.setDaemon(true);
        t1.start();
        t2.start();
        Thread.sleep(150);

        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        long[] deadlockedIds = bean.findDeadlockedThreads();
        if (deadlockedIds != null) {
            System.out.println("  [DEADLOCK DETECTED] " + deadlockedIds.length + " threads are deadlocked (" + t1.getName() + " & " + t2.getName() + ").");
        }

        System.out.println("\n=== Part 2: Resolving Deadlock with Consistent Lock Ordering (Lower ID First) ===");
        BankVault safeA = new BankVault(1, "SafeVault-A", 1000);
        BankVault safeB = new BankVault(2, "SafeVault-B", 1000);

        Thread s1 = new Thread(() -> transferSafe(safeA, safeB, 100));
        Thread s2 = new Thread(() -> transferSafe(safeB, safeA, 250));
        s1.start();
        s2.start();
        s1.join();
        s2.join();

        System.out.println("  Final Balances -> " + safeA.getName() + ": " + safeA.getBalance()
                + ", " + safeB.getName() + ": " + safeB.getBalance());
    }
}
