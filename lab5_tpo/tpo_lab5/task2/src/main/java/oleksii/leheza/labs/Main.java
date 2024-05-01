package oleksii.leheza.labs;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        int progonsCount = 5;
        List<Progon> progons = new ArrayList<>();
        for (int i = 0; i < progonsCount; i++) {
            Progon progon = new Progon();
            progons.add(progon);
            forkJoinPool.execute(progon);
        }
        forkJoinPool.shutdown();
        while (!forkJoinPool.isTerminated()) {
            try {
                forkJoinPool.awaitTermination(20, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        double lostTasks = 0;
        double completedTasks = 0;
        double averageQueueSizeLengths = 0;
        double failureProbabilities = 0;
        for (Progon progon : progons) {
            averageQueueSizeLengths += progon.getAverageQueueSizeLength();
            failureProbabilities += progon.getFailureProbability();
            lostTasks += progon.getLostTasks();
            completedTasks += progon.getCompletedTasks();
        }
        double lostTasksAverage = lostTasks / progonsCount;
        double completedTasksAverage = completedTasks / progonsCount;
        double QueueSizeLengthsAverage = averageQueueSizeLengths / progonsCount;
        double failureProbabilitiesAverage = failureProbabilities / progonsCount;
        System.out.println("Average Lost tasks: " + lostTasksAverage);
        System.out.println("Average Completed tasks: " + completedTasksAverage);
        System.out.println("Average queue size length: " + QueueSizeLengthsAverage);
        System.out.println("Average Probability of failure: " + failureProbabilitiesAverage);
    }
}