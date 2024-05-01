package oleksii.leheza.labs;

import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicInteger;

public class Consumer extends RecursiveAction {

    private AtomicInteger taskCompleted;

    private final Producer producer;

    public Consumer(Producer producer, AtomicInteger taskCompleted) {
        this.producer = producer;
        this.taskCompleted = taskCompleted;
    }

    @Override
    protected void compute() {
        while (true) {
            ClientTask task = producer.poll();
            if (task != null) {
                taskCompleted.incrementAndGet();
                task.run();
            }
            if (producer.isTasksEnd() && task == null) {
                break;
            }
        }
    }
}