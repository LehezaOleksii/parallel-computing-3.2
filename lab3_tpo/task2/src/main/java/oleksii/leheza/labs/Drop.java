package oleksii.leheza.labs;

public class Drop {
    private int number;
    private boolean empty = true;

    public synchronized int take() {
        while (empty) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        empty = true;
        notifyAll();
        return number;
    }

    public synchronized void put(int number) {
        while (!empty) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        empty = false;
        this.number = number;
        notifyAll();
    }
}