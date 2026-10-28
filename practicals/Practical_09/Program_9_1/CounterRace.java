class SharedCounter {
    private int unsafeCount = 0;
    private int safeCount = 0;

    public void incrementUnsafe() {
        int temp = unsafeCount;
        Thread.yield();
        unsafeCount = temp + 1;
    }

    public synchronized void incrementSafe() {
        safeCount++;
    }

    public int getUnsafeCount() {
        return unsafeCount;
    }

    public int getSafeCount() {
        return safeCount;
    }
}

public class CounterRace {
    public static void main(String[] args) throws InterruptedException {
        final int THREADS = 10;
        final int INCREMENTS_PER_THREAD = 10000;
        final int EXPECTED = THREADS * INCREMENTS_PER_THREAD;

        SharedCounter counter = new SharedCounter();

        // 1. Run without synchronization
        Thread[] unsafeThreads = new Thread[THREADS];
        for (int i = 0; i < THREADS; i++) {
            unsafeThreads[i] = new Thread(() -> {
                for (int j = 0; j < INCREMENTS_PER_THREAD; j++) {
                    counter.incrementUnsafe();
                }
            });
            unsafeThreads[i].start();
        }
        for (Thread t : unsafeThreads) {
            t.join();
        }

        // 2. Run with synchronized increment
        Thread[] safeThreads = new Thread[THREADS];
        for (int i = 0; i < THREADS; i++) {
            safeThreads[i] = new Thread(() -> {
                for (int j = 0; j < INCREMENTS_PER_THREAD; j++) {
                    counter.incrementSafe();
                }
            });
            safeThreads[i].start();
        }
        for (Thread t : safeThreads) {
            t.join();
        }

        System.out.println("Expected Count             : " + EXPECTED);
        System.out.println("Unsynchronized Final Count : " + counter.getUnsafeCount() + " (Lost updates due to race condition)");
        System.out.println("Synchronized Final Count   : " + counter.getSafeCount() + " (Exact match)");
    }
}
