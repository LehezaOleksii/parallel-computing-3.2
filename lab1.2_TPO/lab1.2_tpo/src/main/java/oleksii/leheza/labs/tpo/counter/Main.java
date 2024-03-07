package oleksii.leheza.labs.tpo.counter;

public class Main {


    public static void main(String[] args) {
        final Counter counter = new Counter();
        final int iterationSum = 100000;
        Thread syncIncrementMethod = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < iterationSum; i++) {
                    counter.incrementSyncMethod();
                }
            }
        });
        Thread syncDecrementMethod = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < iterationSum; i++) {
                    counter.decrementSyncMethod();
                }
            }
        });
        long startTime = System.currentTimeMillis();
        syncIncrementMethod.start();
        syncDecrementMethod.start();
        try {
            syncIncrementMethod.join();
            syncDecrementMethod.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        System.out.println("sync method time: " + elapsedTime + "; sum: " + counter.getSum());
        Thread syncIncrementThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < iterationSum; i++) {
                    counter.incrementBlock();
                }
            }
        });
        Thread syncDecrementThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < iterationSum; i++) {
                    counter.decrementBlock();
                }
            }
        });
        startTime = System.currentTimeMillis();
        syncIncrementThread.start();
        syncDecrementThread.start();
        try {
            syncIncrementThread.join();
            syncDecrementThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        endTime = System.currentTimeMillis();
        elapsedTime = endTime - startTime;
        System.out.println("sync block method time: " + elapsedTime + "; sum: " + counter.getSum());
        Thread syncIncrementBlockThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < iterationSum; i++) {
                    counter.incrementLockMethod();
                }
            }
        });
        Thread syncDecrementBlockThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < iterationSum; i++) {
                    counter.decrementLockMethod();
                }
            }
        });
        startTime = System.currentTimeMillis();
        syncIncrementBlockThread.start();
        syncDecrementBlockThread.start();
        try {
            syncIncrementBlockThread.join();
            syncDecrementBlockThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        endTime = System.currentTimeMillis();
        elapsedTime = endTime - startTime;
        System.out.println("sync lock method time: " + elapsedTime + "; sum: " + counter.getSum());
    }
}