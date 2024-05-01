package oleksii.leheza.labs;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Progon implements Runnable {


    private int lostTasks;
    private AtomicInteger completedTasks;
    private int allOperations;

    private double averageQueueSizeLength;

    private double failureProbability;

    @Override
    public void run() {
        completedTasks = new AtomicInteger();

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
            try {
                forkJoinPool.awaitTermination(20, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        lostTasks = producer.getTaskLoss();
        allOperations = completedTasks.get() + lostTasks;
        averageQueueSizeLength = (double) sizeTracker.getQueueSizeSum() / sizeTracker.getQueueSizeSumOperations();
        failureProbability = (double) lostTasks / allOperations;
    }

    public double getAverageQueueSizeLength() {
        return averageQueueSizeLength;
    }

    public double getFailureProbability() {
        return failureProbability;
    }
    public int getLostTasks() {
        return lostTasks;
    }

    public int getCompletedTasks() {
        return completedTasks.get();
    }
}
