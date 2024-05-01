package oleksii.leheza.labs;

import java.util.concurrent.atomic.AtomicInteger;

public class ClientTask implements Runnable {

    private static AtomicInteger staticId = new AtomicInteger(1);

    private final int id = staticId.getAndIncrement();

    @Override
    public void run() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
