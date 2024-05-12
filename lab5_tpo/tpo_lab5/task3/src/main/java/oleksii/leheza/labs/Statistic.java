package oleksii.leheza.labs;

import java.util.concurrent.atomic.AtomicInteger;

public class Statistic implements Runnable {

    private final Producer producer;

    private final AtomicInteger completedTasks;

    public Statistic(Producer producer, AtomicInteger completedTasks) {
        this.producer = producer;
        this.completedTasks = completedTasks;
    }

    @Override
    public void run() {
        while (!producer.isTasksEnd()) {
            System.out.println("Statistic\nQueue size:" + producer.getQueueSize() +
                    "\nLost tasks:" + producer.getTaskLoss() +
                    "\nCompleted tasks:" + completedTasks.get()+
                    "\n----------------------------------------------------");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
