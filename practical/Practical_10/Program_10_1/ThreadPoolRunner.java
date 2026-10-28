import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolRunner {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(3);
        System.out.println("Submitting 10 tasks to a fixed thread pool of size 3...");

        for (int i = 1; i <= 10; i++) {
            final int taskId = i;
            pool.submit(() -> {
                System.out.println("Task #" + String.format("%02d", taskId)
                        + " executed by " + Thread.currentThread().getName());
                try {
                    Thread.sleep(40);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        pool.shutdown();
        boolean finished = pool.awaitTermination(5, TimeUnit.SECONDS);
        System.out.println("All tasks completed (terminated=" + finished + "). Notice how only 3 pool threads were reused.");
    }
}
