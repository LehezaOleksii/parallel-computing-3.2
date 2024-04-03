package oleksii.leheza.labs;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class MKP {
    public static void main(String[] args) {
        int[] array = createArray(1000000);
        int numberOfTasks = 100;
        ExecutorService executor = Executors.newFixedThreadPool(8);
        int chunkSize = array.length / numberOfTasks;
        AtomicInteger totalSum = new AtomicInteger(0);

        for (int i = 0; i < numberOfTasks; i++) {
            int start = i * chunkSize;
            int end = (i == numberOfTasks - 1) ? array.length : (i + 1) * chunkSize;

            Runnable task = new SumCalculator(array, start, end, totalSum);
            executor.execute(task);
        }

        executor.shutdown();

        try {
            executor.awaitTermination(100, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Sum: " + totalSum.get());
    }

    private static int[] createArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = (int) (Math.random() * 100);
        }
        return array;
    }
}

class SumCalculator implements Runnable {
    private final int[] array;
    private final int start;
    private final int end;
    private final AtomicInteger totalSum;

    public SumCalculator(int[] array, int start, int end, AtomicInteger totalSum) {
        this.array = array;
        this.start = start;
        this.end = end;
        this.totalSum = totalSum;
    }

    @Override
    public void run() {
        int localSum = 0;
        for (int i = start; i < end; i++) {
            localSum += array[i];
        }
        totalSum.addAndGet(localSum);
    }
}