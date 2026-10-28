class SharedBuffer {
    private final int[] buffer;
    private int count = 0;
    private int in = 0;
    private int out = 0;

    public SharedBuffer(int capacity) {
        this.buffer = new int[capacity];
    }

    public synchronized void produce(int item) throws InterruptedException {
        while (count == buffer.length) {
            wait();
        }
        buffer[in] = item;
        in = (in + 1) % buffer.length;
        count++;
        System.out.println("Produced: " + item + " (Buffer occupancy: " + count + "/" + buffer.length + ")");
        notifyAll();
    }

    public synchronized int consume() throws InterruptedException {
        while (count == 0) {
            wait();
        }
        int item = buffer[out];
        out = (out + 1) % buffer.length;
        count--;
        System.out.println("Consumed: " + item + " (Buffer occupancy: " + count + "/" + buffer.length + ")");
        notifyAll();
        return item;
    }
}

public class ProducerConsumerDemo {
    public static void main(String[] args) throws InterruptedException {
        SharedBuffer buffer = new SharedBuffer(3);
        final int TOTAL_ITEMS = 6;

        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= TOTAL_ITEMS; i++) {
                    buffer.produce(i * 10);
                    Thread.sleep(20);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 1; i <= TOTAL_ITEMS; i++) {
                    buffer.consume();
                    Thread.sleep(35);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        System.out.println("Completed producing and consuming all " + TOTAL_ITEMS + " items in order with none lost.");
    }
}
