package oleksii.leheza.labs;

public class ClientTask implements Runnable {


    @Override
    public void run() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
