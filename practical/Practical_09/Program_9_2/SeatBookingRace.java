import java.util.concurrent.atomic.AtomicInteger;

class TicketCounter {
    private int seatsLeft;
    private final AtomicInteger successfulBookings = new AtomicInteger(0);

    public TicketCounter(int seatsLeft) {
        this.seatsLeft = seatsLeft;
    }

    public void bookUnsafe(String passenger) {
        if (seatsLeft > 0) {
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            seatsLeft--;
            int bookedNum = successfulBookings.incrementAndGet();
            System.out.println("  [UNSAFE] " + passenger + " booked a seat! (Total booked: " + bookedNum + ", seatsLeft: " + seatsLeft + ")");
        } else {
            System.out.println("  [UNSAFE] " + passenger + " failed to book (No seats left).");
        }
    }

    public synchronized void bookSafe(String passenger) {
        if (seatsLeft > 0) {
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            seatsLeft--;
            int bookedNum = successfulBookings.incrementAndGet();
            System.out.println("  [SAFE]   " + passenger + " booked a seat! (Total booked: " + bookedNum + ", seatsLeft: " + seatsLeft + ")");
        } else {
            System.out.println("  [SAFE]   " + passenger + " refused (Sold Out).");
        }
    }

    public int getSuccessfulBookings() {
        return successfulBookings.get();
    }
}

public class SeatBookingRace {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== 1. Unsynchronized Booking (5 seats, 10 threads) ===");
        TicketCounter unsafeCounter = new TicketCounter(5);
        Thread[] t1 = new Thread[10];
        for (int i = 0; i < 10; i++) {
            final String name = "User-" + (i + 1);
            t1[i] = new Thread(() -> unsafeCounter.bookUnsafe(name));
            t1[i].start();
        }
        for (Thread t : t1) t.join();
        System.out.println("Unsynchronized Total Succeeded: " + unsafeCounter.getSuccessfulBookings() + " (Oversold!)\n");

        System.out.println("=== 2. Synchronized Booking (5 seats, 10 threads) ===");
        TicketCounter safeCounter = new TicketCounter(5);
        Thread[] t2 = new Thread[10];
        for (int i = 0; i < 10; i++) {
            final String name = "User-" + (i + 1);
            t2[i] = new Thread(() -> safeCounter.bookSafe(name));
            t2[i].start();
        }
        for (Thread t : t2) t.join();
        System.out.println("Synchronized Total Succeeded  : " + safeCounter.getSuccessfulBookings() + " (Exactly 5 seats sold)");
    }
}
