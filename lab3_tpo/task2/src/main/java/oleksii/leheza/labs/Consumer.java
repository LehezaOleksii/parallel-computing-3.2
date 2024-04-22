package oleksii.leheza.labs;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Consumer implements Runnable {
    private final Drop drop;
    private AtomicInteger iterationNumber;

    public Consumer(Drop drop, AtomicInteger iterationNumber) {
        this.drop = drop;
        this.iterationNumber = iterationNumber;
    }

    public void run() {
        Random random = new Random();
        for (int number = drop.take(); number != -1; number = drop.take()) {
            System.out.format(iterationNumber.getAndIncrement() + ") NUMBER RECEIVED: %s%n", number);
            try {
                Thread.sleep(random.nextInt(1));
            } catch (InterruptedException e) {
            }
            ;
        }
    }
}