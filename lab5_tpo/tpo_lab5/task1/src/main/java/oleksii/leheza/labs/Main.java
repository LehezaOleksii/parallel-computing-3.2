package oleksii.leheza.labs;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static void main(String[] args) throws InterruptedException {



        AtomicInteger completedTasks = new AtomicInteger();

        Producer producer = new Producer();
        ForkJoinPool forkJoinPool = new ForkJoinPool(8);

        forkJoinPool.execute(producer);

        QueueSizeTracker sizeTracker = new QueueSizeTracker(producer);
        forkJoinPool.execute(sizeTracker);

        for (int i = 0; i < 10; i++) {
            Consumer consumer = new Consumer(producer, completedTasks);
            forkJoinPool.execute(consumer);
        }

        forkJoinPool.shutdown();
        while (!forkJoinPool.isTerminated()) {
            forkJoinPool.awaitTermination(20, TimeUnit.SECONDS);
        }

        int lostTasks = producer.getTaskLoss();
        int allOperations = completedTasks.get() + lostTasks;
        System.out.println("Lost tasks: " + lostTasks);
        System.out.println("Completed tasks: " + completedTasks.get());
        System.out.println("Average queue size length: " + (double) sizeTracker.getQueueSizeSum() / sizeTracker.getQueueSizeSumOperations());
        System.out.println("Probability of failure: " + (double) lostTasks / allOperations);

    }
}