package service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * TransactionProcessor — manages transaction execution on a fixed thread pool.
 * Demonstrates ExecutorService usage, batch submission, and clean shutdown.
 */
public class TransactionProcessor {

    private final ExecutorService executor;

    public TransactionProcessor(int threads) {
        this.executor = Executors.newFixedThreadPool(threads);
    }

    public TransactionProcessor() {
        this(4);
    }

    /**
     * Submits a transaction task to the thread pool for asynchronous execution.
     */
    public void submit(Runnable task) {
        executor.execute(task);
    }

    /**
     * Initiates an orderly shutdown and waits for running tasks to terminate.
     */
    public void stop() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
