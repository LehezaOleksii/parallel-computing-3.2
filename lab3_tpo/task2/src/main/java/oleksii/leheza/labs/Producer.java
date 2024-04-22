package oleksii.leheza.labs;

import java.util.Random;

public class Producer implements Runnable {
    private final Drop drop;
    private final int infoLength;

    public Producer(Drop drop, int infoLength) {
        this.drop = drop;
        this.infoLength = infoLength;
    }

    public void run() {
        int[] importantInfo = new int[infoLength];
        Random random = new Random();
        for (int i = 0; i < infoLength; i++) {
            importantInfo[i] = i;
        }

        for (int i = 0; i < infoLength; i++) {
            drop.put(importantInfo[i]);
            try {
                Thread.sleep(random.nextInt(1));
            } catch (InterruptedException e) {
            }
        }
        drop.put(-1);
    }
}