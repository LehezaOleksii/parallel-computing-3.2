package oleksii.leheza.labs;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class Producer implements Runnable {
    private final Queue<ClientTask> queue = new ConcurrentLinkedQueue<>();
    private final int maxQueueLength = 100;
    private int taskLoss = 0;

    private AtomicBoolean isTasksEnd = new AtomicBoolean();

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            try {
                Thread.sleep(9);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            add(new ClientTask());
        }
        isTasksEnd.set(true);
    }

    public synchronized void add(ClientTask task) {
        if (queue.size() < maxQueueLength) {
            queue.add(task);
        } else {
            taskLoss += 1;
        }
    }

    public synchronized ClientTask poll() {
        return queue.poll();
    }

    public int getTaskLoss() {
        return taskLoss;
    }

    public boolean isTasksEnd() {
        return isTasksEnd.get();
    }

    public int getQueueSize() {
        return queue.size();
    }
}
