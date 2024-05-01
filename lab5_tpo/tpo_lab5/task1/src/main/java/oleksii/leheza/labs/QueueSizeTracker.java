package oleksii.leheza.labs;

class QueueSizeTracker implements Runnable {
    private final Producer producer;
    private int queueSizeSum = 0;
    private int queueSizeSumOperations = 0;

    public QueueSizeTracker(Producer producer) {
        this.producer = producer;
    }

    @Override
    public void run() {
        while (!producer.isTasksEnd()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            queueSizeSum += producer.getQueueSize();
            queueSizeSumOperations += 1;
        }
    }

    public int getQueueSizeSum() {
        return queueSizeSum;
    }

    public int getQueueSizeSumOperations() {
        return queueSizeSumOperations;
    }
}