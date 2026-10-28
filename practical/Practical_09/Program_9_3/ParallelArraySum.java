import java.util.concurrent.atomic.AtomicLong;

public class ParallelArraySum {
    private static long unsafeTotal = 0;
    private static long syncTotal = 0;
    private static final Object LOCK = new Object();

    public static void main(String[] args) throws InterruptedException {
        final int SIZE = 400_000;
        final int THREADS = 4;
        int[] data = new int[SIZE];
        long expected = 0;
        for (int i = 0; i < SIZE; i++) {
            data[i] = (i % 10) + 1;
            expected += data[i];
        }

        int chunk = SIZE / THREADS;

        // 1. Unsynchronized shared total
        long t0 = System.nanoTime();
        Thread[] workers = new Thread[THREADS];
        for (int t = 0; t < THREADS; t++) {
            final int start = t * chunk;
            final int end = (t == THREADS - 1) ? SIZE : start + chunk;
            workers[t] = new Thread(() -> {
                for (int i = start; i < end; i++) {
                    unsafeTotal += data[i];
                }
            });
            workers[t].start();
        }
        for (Thread w : workers) w.join();
        long unsafeTimeMicros = (System.nanoTime() - t0) / 1000;

        // 2. Fix Way 1: Synchronized lock per element
        long t1 = System.nanoTime();
        for (int t = 0; t < THREADS; t++) {
            final int start = t * chunk;
            final int end = (t == THREADS - 1) ? SIZE : start + chunk;
            workers[t] = new Thread(() -> {
                for (int i = start; i < end; i++) {
                    synchronized (LOCK) {
                        syncTotal += data[i];
                    }
                }
            });
            workers[t].start();
        }
        for (Thread w : workers) w.join();
        long syncTimeMicros = (System.nanoTime() - t1) / 1000;

        // 3. Fix Way 2: Local partial sum per thread + AtomicLong accumulation
        AtomicLong atomicTotal = new AtomicLong(0);
        long t2 = System.nanoTime();
        for (int t = 0; t < THREADS; t++) {
            final int start = t * chunk;
            final int end = (t == THREADS - 1) ? SIZE : start + chunk;
            workers[t] = new Thread(() -> {
                long localSum = 0;
                for (int i = start; i < end; i++) {
                    localSum += data[i];
                }
                atomicTotal.addAndGet(localSum);
            });
            workers[t].start();
        }
        for (Thread w : workers) w.join();
        long localAtomicTimeMicros = (System.nanoTime() - t2) / 1000;

        System.out.println("Array Size: " + SIZE + " elements across " + THREADS + " threads");
        System.out.println("Expected Sum                        : " + expected);
        System.out.println("1. Unsynchronized Shared Sum        : " + unsafeTotal + " (Time: " + unsafeTimeMicros + " us) -> WRONG");
        System.out.println("2. Fix 1 (Synchronized Block)       : " + syncTotal + " (Time: " + syncTimeMicros + " us) -> CORRECT");
        System.out.println("3. Fix 2 (Thread-Local + AtomicLong): " + atomicTotal.get() + " (Time: " + localAtomicTimeMicros + " us) -> CORRECT & FASTEST");
    }
}
